import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFacilityGroupMember, FacilityGroupMember } from 'app/shared/model/facility-group-member.model';
import { FacilityGroupMemberService } from './facility-group-member.service';
import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from 'app/entities/facility/facility.service';
import { IFacilityGroup } from 'app/shared/model/facility-group.model';
import { FacilityGroupService } from 'app/entities/facility-group/facility-group.service';

type SelectableEntity = IFacility | IFacilityGroup;

@Component({
  selector: 'sys-facility-group-member-update',
  templateUrl: './facility-group-member-update.component.html',
})
export class FacilityGroupMemberUpdateComponent implements OnInit {
  isSaving = false;
  facilities: IFacility[] = [];
  facilitygroups: IFacilityGroup[] = [];

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    sequenceNo: [],
    facility: [],
    facilityGroup: [],
  });

  constructor(
    protected facilityGroupMemberService: FacilityGroupMemberService,
    protected facilityService: FacilityService,
    protected facilityGroupService: FacilityGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facilityGroupMember }) => {
      if (!facilityGroupMember.id) {
        const today = moment().startOf('day');
        facilityGroupMember.fromDate = today;
        facilityGroupMember.thruDate = today;
      }

      this.updateForm(facilityGroupMember);

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));

      this.facilityGroupService.query().subscribe((res: HttpResponse<IFacilityGroup[]>) => (this.facilitygroups = res.body || []));
    });
  }

  updateForm(facilityGroupMember: IFacilityGroupMember): void {
    this.editForm.patchValue({
      id: facilityGroupMember.id,
      fromDate: facilityGroupMember.fromDate ? facilityGroupMember.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: facilityGroupMember.thruDate ? facilityGroupMember.thruDate.format(DATE_TIME_FORMAT) : null,
      sequenceNo: facilityGroupMember.sequenceNo,
      facility: facilityGroupMember.facility,
      facilityGroup: facilityGroupMember.facilityGroup,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facilityGroupMember = this.createFromForm();
    if (facilityGroupMember.id !== undefined) {
      this.subscribeToSaveResponse(this.facilityGroupMemberService.update(facilityGroupMember));
    } else {
      this.subscribeToSaveResponse(this.facilityGroupMemberService.create(facilityGroupMember));
    }
  }

  private createFromForm(): IFacilityGroupMember {
    return {
      ...new FacilityGroupMember(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? moment(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      facility: this.editForm.get(['facility'])!.value,
      facilityGroup: this.editForm.get(['facilityGroup'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacilityGroupMember>>): void {
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
