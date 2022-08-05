import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPartyRole, PartyRole } from 'app/shared/model/party-role.model';
import { PartyRoleService } from './party-role.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IRoleType } from 'app/shared/model/role-type.model';
import { RoleTypeService } from 'app/entities/role-type/role-type.service';

type SelectableEntity = IParty | IRoleType;

@Component({
  selector: 'sys-party-role-update',
  templateUrl: './party-role-update.component.html',
})
export class PartyRoleUpdateComponent implements OnInit {
  isSaving = false;
  parties: IParty[] = [];
  roletypes: IRoleType[] = [];

  editForm = this.fb.group({
    id: [],
    party: [],
    roleType: [],
  });

  constructor(
    protected partyRoleService: PartyRoleService,
    protected partyService: PartyService,
    protected roleTypeService: RoleTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyRole }) => {
      this.updateForm(partyRole);

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.roleTypeService.query().subscribe((res: HttpResponse<IRoleType[]>) => (this.roletypes = res.body || []));
    });
  }

  updateForm(partyRole: IPartyRole): void {
    this.editForm.patchValue({
      id: partyRole.id,
      party: partyRole.party,
      roleType: partyRole.roleType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyRole = this.createFromForm();
    if (partyRole.id !== undefined) {
      this.subscribeToSaveResponse(this.partyRoleService.update(partyRole));
    } else {
      this.subscribeToSaveResponse(this.partyRoleService.create(partyRole));
    }
  }

  private createFromForm(): IPartyRole {
    return {
      ...new PartyRole(),
      id: this.editForm.get(['id'])!.value,
      party: this.editForm.get(['party'])!.value,
      roleType: this.editForm.get(['roleType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyRole>>): void {
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
