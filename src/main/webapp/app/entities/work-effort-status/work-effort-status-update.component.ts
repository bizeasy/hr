import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorkEffortStatus, WorkEffortStatus } from 'app/shared/model/work-effort-status.model';
import { WorkEffortStatusService } from './work-effort-status.service';
import { IWorkEffort } from 'app/shared/model/work-effort.model';
import { WorkEffortService } from 'app/entities/work-effort/work-effort.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';

type SelectableEntity = IWorkEffort | IStatus;

@Component({
  selector: 'sys-work-effort-status-update',
  templateUrl: './work-effort-status-update.component.html',
})
export class WorkEffortStatusUpdateComponent implements OnInit {
  isSaving = false;
  workefforts: IWorkEffort[] = [];
  statuses: IStatus[] = [];

  editForm = this.fb.group({
    id: [],
    reason: [],
    workEffort: [],
    status: [],
  });

  constructor(
    protected workEffortStatusService: WorkEffortStatusService,
    protected workEffortService: WorkEffortService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffortStatus }) => {
      this.updateForm(workEffortStatus);

      this.workEffortService.query().subscribe((res: HttpResponse<IWorkEffort[]>) => (this.workefforts = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));
    });
  }

  updateForm(workEffortStatus: IWorkEffortStatus): void {
    this.editForm.patchValue({
      id: workEffortStatus.id,
      reason: workEffortStatus.reason,
      workEffort: workEffortStatus.workEffort,
      status: workEffortStatus.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workEffortStatus = this.createFromForm();
    if (workEffortStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.workEffortStatusService.update(workEffortStatus));
    } else {
      this.subscribeToSaveResponse(this.workEffortStatusService.create(workEffortStatus));
    }
  }

  private createFromForm(): IWorkEffortStatus {
    return {
      ...new WorkEffortStatus(),
      id: this.editForm.get(['id'])!.value,
      reason: this.editForm.get(['reason'])!.value,
      workEffort: this.editForm.get(['workEffort'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkEffortStatus>>): void {
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
