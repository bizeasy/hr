import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmplLeave, EmplLeave } from 'app/shared/model/empl-leave.model';
import { EmplLeaveService } from './empl-leave.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IEmplLeaveType } from 'app/shared/model/empl-leave-type.model';
import { EmplLeaveTypeService } from 'app/entities/empl-leave-type/empl-leave-type.service';
import { IEmplLeaveReasonType } from 'app/shared/model/empl-leave-reason-type.model';
import { EmplLeaveReasonTypeService } from 'app/entities/empl-leave-reason-type/empl-leave-reason-type.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';

type SelectableEntity = IParty | IEmplLeaveType | IEmplLeaveReasonType | IStatus;

@Component({
  selector: 'sys-empl-leave-update',
  templateUrl: './empl-leave-update.component.html',
})
export class EmplLeaveUpdateComponent implements OnInit {
  isSaving = false;
  parties: IParty[] = [];
  emplleavetypes: IEmplLeaveType[] = [];
  emplleavereasontypes: IEmplLeaveReasonType[] = [];
  statuses: IStatus[] = [];
  fromDateDp: any;
  thruDateDp: any;

  editForm = this.fb.group({
    id: [],
    description: [],
    fromDate: [],
    thruDate: [],
    employee: [],
    approver: [],
    leaveType: [],
    reason: [],
    status: [],
  });

  constructor(
    protected emplLeaveService: EmplLeaveService,
    protected partyService: PartyService,
    protected emplLeaveTypeService: EmplLeaveTypeService,
    protected emplLeaveReasonTypeService: EmplLeaveReasonTypeService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplLeave }) => {
      this.updateForm(emplLeave);

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.emplLeaveTypeService.query().subscribe((res: HttpResponse<IEmplLeaveType[]>) => (this.emplleavetypes = res.body || []));

      this.emplLeaveReasonTypeService
        .query()
        .subscribe((res: HttpResponse<IEmplLeaveReasonType[]>) => (this.emplleavereasontypes = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));
    });
  }

  updateForm(emplLeave: IEmplLeave): void {
    this.editForm.patchValue({
      id: emplLeave.id,
      description: emplLeave.description,
      fromDate: emplLeave.fromDate,
      thruDate: emplLeave.thruDate,
      employee: emplLeave.employee,
      approver: emplLeave.approver,
      leaveType: emplLeave.leaveType,
      reason: emplLeave.reason,
      status: emplLeave.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emplLeave = this.createFromForm();
    if (emplLeave.id !== undefined) {
      this.subscribeToSaveResponse(this.emplLeaveService.update(emplLeave));
    } else {
      this.subscribeToSaveResponse(this.emplLeaveService.create(emplLeave));
    }
  }

  private createFromForm(): IEmplLeave {
    return {
      ...new EmplLeave(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      thruDate: this.editForm.get(['thruDate'])!.value,
      employee: this.editForm.get(['employee'])!.value,
      approver: this.editForm.get(['approver'])!.value,
      leaveType: this.editForm.get(['leaveType'])!.value,
      reason: this.editForm.get(['reason'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmplLeave>>): void {
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
