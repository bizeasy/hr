import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IWorkEffortAssoc, WorkEffortAssoc } from 'app/shared/model/work-effort-assoc.model';
import { WorkEffortAssocService } from './work-effort-assoc.service';
import { IWorkEffortAssocType } from 'app/shared/model/work-effort-assoc-type.model';
import { WorkEffortAssocTypeService } from 'app/entities/work-effort-assoc-type/work-effort-assoc-type.service';
import { IWorkEffort } from 'app/shared/model/work-effort.model';
import { WorkEffortService } from 'app/entities/work-effort/work-effort.service';

type SelectableEntity = IWorkEffortAssocType | IWorkEffort;

@Component({
  selector: 'sys-work-effort-assoc-update',
  templateUrl: './work-effort-assoc-update.component.html',
})
export class WorkEffortAssocUpdateComponent implements OnInit {
  isSaving = false;
  workeffortassoctypes: IWorkEffortAssocType[] = [];
  workefforts: IWorkEffort[] = [];

  editForm = this.fb.group({
    id: [],
    sequenceNo: [],
    fromDate: [],
    thruDate: [],
    type: [],
    weIdFrom: [],
    weIdTo: [],
  });

  constructor(
    protected workEffortAssocService: WorkEffortAssocService,
    protected workEffortAssocTypeService: WorkEffortAssocTypeService,
    protected workEffortService: WorkEffortService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffortAssoc }) => {
      if (!workEffortAssoc.id) {
        const today = moment().startOf('day');
        workEffortAssoc.fromDate = today;
        workEffortAssoc.thruDate = today;
      }

      this.updateForm(workEffortAssoc);

      this.workEffortAssocTypeService
        .query()
        .subscribe((res: HttpResponse<IWorkEffortAssocType[]>) => (this.workeffortassoctypes = res.body || []));

      this.workEffortService.query().subscribe((res: HttpResponse<IWorkEffort[]>) => (this.workefforts = res.body || []));
    });
  }

  updateForm(workEffortAssoc: IWorkEffortAssoc): void {
    this.editForm.patchValue({
      id: workEffortAssoc.id,
      sequenceNo: workEffortAssoc.sequenceNo,
      fromDate: workEffortAssoc.fromDate ? workEffortAssoc.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: workEffortAssoc.thruDate ? workEffortAssoc.thruDate.format(DATE_TIME_FORMAT) : null,
      type: workEffortAssoc.type,
      weIdFrom: workEffortAssoc.weIdFrom,
      weIdTo: workEffortAssoc.weIdTo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workEffortAssoc = this.createFromForm();
    if (workEffortAssoc.id !== undefined) {
      this.subscribeToSaveResponse(this.workEffortAssocService.update(workEffortAssoc));
    } else {
      this.subscribeToSaveResponse(this.workEffortAssocService.create(workEffortAssoc));
    }
  }

  private createFromForm(): IWorkEffortAssoc {
    return {
      ...new WorkEffortAssoc(),
      id: this.editForm.get(['id'])!.value,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? moment(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      type: this.editForm.get(['type'])!.value,
      weIdFrom: this.editForm.get(['weIdFrom'])!.value,
      weIdTo: this.editForm.get(['weIdTo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkEffortAssoc>>): void {
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
