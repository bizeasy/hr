import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductStoreUserGroup, ProductStoreUserGroup } from 'app/shared/model/product-store-user-group.model';
import { ProductStoreUserGroupService } from './product-store-user-group.service';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { UserGroupService } from 'app/entities/user-group/user-group.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IProductStore } from 'app/shared/model/product-store.model';
import { ProductStoreService } from 'app/entities/product-store/product-store.service';

type SelectableEntity = IUserGroup | IUser | IParty | IProductStore;

@Component({
  selector: 'sys-product-store-user-group-update',
  templateUrl: './product-store-user-group-update.component.html',
})
export class ProductStoreUserGroupUpdateComponent implements OnInit {
  isSaving = false;
  usergroups: IUserGroup[] = [];
  users: IUser[] = [];
  parties: IParty[] = [];
  productstores: IProductStore[] = [];

  editForm = this.fb.group({
    id: [],
    userGroup: [],
    user: [],
    party: [],
    productStore: [null, Validators.required],
  });

  constructor(
    protected productStoreUserGroupService: ProductStoreUserGroupService,
    protected userGroupService: UserGroupService,
    protected userService: UserService,
    protected partyService: PartyService,
    protected productStoreService: ProductStoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productStoreUserGroup }) => {
      this.updateForm(productStoreUserGroup);

      this.userGroupService.query().subscribe((res: HttpResponse<IUserGroup[]>) => (this.usergroups = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.productStoreService.query().subscribe((res: HttpResponse<IProductStore[]>) => (this.productstores = res.body || []));
    });
  }

  updateForm(productStoreUserGroup: IProductStoreUserGroup): void {
    this.editForm.patchValue({
      id: productStoreUserGroup.id,
      userGroup: productStoreUserGroup.userGroup,
      user: productStoreUserGroup.user,
      party: productStoreUserGroup.party,
      productStore: productStoreUserGroup.productStore,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productStoreUserGroup = this.createFromForm();
    if (productStoreUserGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.productStoreUserGroupService.update(productStoreUserGroup));
    } else {
      this.subscribeToSaveResponse(this.productStoreUserGroupService.create(productStoreUserGroup));
    }
  }

  private createFromForm(): IProductStoreUserGroup {
    return {
      ...new ProductStoreUserGroup(),
      id: this.editForm.get(['id'])!.value,
      userGroup: this.editForm.get(['userGroup'])!.value,
      user: this.editForm.get(['user'])!.value,
      party: this.editForm.get(['party'])!.value,
      productStore: this.editForm.get(['productStore'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductStoreUserGroup>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
