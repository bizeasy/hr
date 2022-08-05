import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFacilityParty, FacilityParty } from 'app/shared/model/facility-party.model';
import { FacilityPartyService } from './facility-party.service';
import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from 'app/entities/facility/facility.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IRoleType } from 'app/shared/model/role-type.model';
import { RoleTypeService } from 'app/entities/role-type/role-type.service';

type SelectableEntity = IFacility | IParty | IRoleType;

@Component({
  selector: 'sys-facility-party-update',
  templateUrl: './facility-party-update.component.html',
})
export class FacilityPartyUpdateComponent implements OnInit {
  isSaving = false;
  facilities: IFacility[] = [];
  parties: IParty[] = [];
  roletypes: IRoleType[] = [];

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    facility: [],
    party: [],
    roleType: [],
  });

  constructor(
    protected facilityPartyService: FacilityPartyService,
    protected facilityService: FacilityService,
    protected partyService: PartyService,
    protected roleTypeService: RoleTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facilityParty }) => {
      if (!facilityParty.id) {
        const today = moment().startOf('day');
        facilityParty.fromDate = today;
        facilityParty.thruDate = today;
      }

      this.updateForm(facilityParty);

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.roleTypeService.query().subscribe((res: HttpResponse<IRoleType[]>) => (this.roletypes = res.body || []));
    });
  }

  updateForm(facilityParty: IFacilityParty): void {
    this.editForm.patchValue({
      id: facilityParty.id,
      fromDate: facilityParty.fromDate ? facilityParty.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: facilityParty.thruDate ? facilityParty.thruDate.format(DATE_TIME_FORMAT) : null,
      facility: facilityParty.facility,
      party: facilityParty.party,
      roleType: facilityParty.roleType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facilityParty = this.createFromForm();
    if (facilityParty.id !== undefined) {
      this.subscribeToSaveResponse(this.facilityPartyService.update(facilityParty));
    } else {
      this.subscribeToSaveResponse(this.facilityPartyService.create(facilityParty));
    }
  }

  private createFromForm(): IFacilityParty {
    return {
      ...new FacilityParty(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? moment(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      facility: this.editForm.get(['facility'])!.value,
      party: this.editForm.get(['party'])!.value,
      roleType: this.editForm.get(['roleType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacilityParty>>): void {
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
