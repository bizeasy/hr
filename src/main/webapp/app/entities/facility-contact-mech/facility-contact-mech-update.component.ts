import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFacilityContactMech, FacilityContactMech } from 'app/shared/model/facility-contact-mech.model';
import { FacilityContactMechService } from './facility-contact-mech.service';
import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from 'app/entities/facility/facility.service';
import { IContactMech } from 'app/shared/model/contact-mech.model';
import { ContactMechService } from 'app/entities/contact-mech/contact-mech.service';
import { IContactMechPurpose } from 'app/shared/model/contact-mech-purpose.model';
import { ContactMechPurposeService } from 'app/entities/contact-mech-purpose/contact-mech-purpose.service';

type SelectableEntity = IFacility | IContactMech | IContactMechPurpose;

@Component({
  selector: 'sys-facility-contact-mech-update',
  templateUrl: './facility-contact-mech-update.component.html',
})
export class FacilityContactMechUpdateComponent implements OnInit {
  isSaving = false;
  facilities: IFacility[] = [];
  contactmeches: IContactMech[] = [];
  contactmechpurposes: IContactMechPurpose[] = [];

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    facility: [],
    contactMech: [],
    contactMechPurpose: [],
  });

  constructor(
    protected facilityContactMechService: FacilityContactMechService,
    protected facilityService: FacilityService,
    protected contactMechService: ContactMechService,
    protected contactMechPurposeService: ContactMechPurposeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facilityContactMech }) => {
      if (!facilityContactMech.id) {
        const today = moment().startOf('day');
        facilityContactMech.fromDate = today;
        facilityContactMech.thruDate = today;
      }

      this.updateForm(facilityContactMech);

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));

      this.contactMechService.query().subscribe((res: HttpResponse<IContactMech[]>) => (this.contactmeches = res.body || []));

      this.contactMechPurposeService
        .query()
        .subscribe((res: HttpResponse<IContactMechPurpose[]>) => (this.contactmechpurposes = res.body || []));
    });
  }

  updateForm(facilityContactMech: IFacilityContactMech): void {
    this.editForm.patchValue({
      id: facilityContactMech.id,
      fromDate: facilityContactMech.fromDate ? facilityContactMech.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: facilityContactMech.thruDate ? facilityContactMech.thruDate.format(DATE_TIME_FORMAT) : null,
      facility: facilityContactMech.facility,
      contactMech: facilityContactMech.contactMech,
      contactMechPurpose: facilityContactMech.contactMechPurpose,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facilityContactMech = this.createFromForm();
    if (facilityContactMech.id !== undefined) {
      this.subscribeToSaveResponse(this.facilityContactMechService.update(facilityContactMech));
    } else {
      this.subscribeToSaveResponse(this.facilityContactMechService.create(facilityContactMech));
    }
  }

  private createFromForm(): IFacilityContactMech {
    return {
      ...new FacilityContactMech(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? moment(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      facility: this.editForm.get(['facility'])!.value,
      contactMech: this.editForm.get(['contactMech'])!.value,
      contactMechPurpose: this.editForm.get(['contactMechPurpose'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacilityContactMech>>): void {
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
