import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductStore, ProductStore } from 'app/shared/model/product-store.model';
import { ProductStoreService } from './product-store.service';
import { IProductStoreType } from 'app/shared/model/product-store-type.model';
import { ProductStoreTypeService } from 'app/entities/product-store-type/product-store-type.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IPostalAddress } from 'app/shared/model/postal-address.model';
import { PostalAddressService } from 'app/entities/postal-address/postal-address.service';

type SelectableEntity = IProductStoreType | IProductStore | IParty | IPostalAddress;

@Component({
  selector: 'sys-product-store-update',
  templateUrl: './product-store-update.component.html',
})
export class ProductStoreUpdateComponent implements OnInit {
  isSaving = false;
  productstoretypes: IProductStoreType[] = [];
  productstores: IProductStore[] = [];
  parties: IParty[] = [];
  postaladdresses: IPostalAddress[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(100)]],
    title: [null, [Validators.maxLength(100)]],
    subtitle: [],
    imageUrl: [],
    comments: [],
    code: [null, [Validators.maxLength(20)]],
    type: [],
    parent: [],
    owner: [],
    postalAddress: [],
  });

  constructor(
    protected productStoreService: ProductStoreService,
    protected productStoreTypeService: ProductStoreTypeService,
    protected partyService: PartyService,
    protected postalAddressService: PostalAddressService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productStore }) => {
      this.updateForm(productStore);

      this.productStoreTypeService.query().subscribe((res: HttpResponse<IProductStoreType[]>) => (this.productstoretypes = res.body || []));

      this.productStoreService.query().subscribe((res: HttpResponse<IProductStore[]>) => (this.productstores = res.body || []));

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.postalAddressService.query().subscribe((res: HttpResponse<IPostalAddress[]>) => (this.postaladdresses = res.body || []));
    });
  }

  updateForm(productStore: IProductStore): void {
    this.editForm.patchValue({
      id: productStore.id,
      name: productStore.name,
      title: productStore.title,
      subtitle: productStore.subtitle,
      imageUrl: productStore.imageUrl,
      comments: productStore.comments,
      code: productStore.code,
      type: productStore.type,
      parent: productStore.parent,
      owner: productStore.owner,
      postalAddress: productStore.postalAddress,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productStore = this.createFromForm();
    if (productStore.id !== undefined) {
      this.subscribeToSaveResponse(this.productStoreService.update(productStore));
    } else {
      this.subscribeToSaveResponse(this.productStoreService.create(productStore));
    }
  }

  private createFromForm(): IProductStore {
    return {
      ...new ProductStore(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      title: this.editForm.get(['title'])!.value,
      subtitle: this.editForm.get(['subtitle'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      code: this.editForm.get(['code'])!.value,
      type: this.editForm.get(['type'])!.value,
      parent: this.editForm.get(['parent'])!.value,
      owner: this.editForm.get(['owner'])!.value,
      postalAddress: this.editForm.get(['postalAddress'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductStore>>): void {
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
