import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmplPositionReportingStruct, EmplPositionReportingStruct } from 'app/shared/model/empl-position-reporting-struct.model';
import { EmplPositionReportingStructService } from './empl-position-reporting-struct.service';

@Component({
  selector: 'sys-empl-position-reporting-struct-update',
  templateUrl: './empl-position-reporting-struct-update.component.html',
})
export class EmplPositionReportingStructUpdateComponent implements OnInit {
  isSaving = false;
  fromDateDp: any;
  thruDateDp: any;

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    comments: [],
  });

  constructor(
    protected emplPositionReportingStructService: EmplPositionReportingStructService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplPositionReportingStruct }) => {
      this.updateForm(emplPositionReportingStruct);
    });
  }

  updateForm(emplPositionReportingStruct: IEmplPositionReportingStruct): void {
    this.editForm.patchValue({
      id: emplPositionReportingStruct.id,
      fromDate: emplPositionReportingStruct.fromDate,
      thruDate: emplPositionReportingStruct.thruDate,
      comments: emplPositionReportingStruct.comments,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emplPositionReportingStruct = this.createFromForm();
    if (emplPositionReportingStruct.id !== undefined) {
      this.subscribeToSaveResponse(this.emplPositionReportingStructService.update(emplPositionReportingStruct));
    } else {
      this.subscribeToSaveResponse(this.emplPositionReportingStructService.create(emplPositionReportingStruct));
    }
  }

  private createFromForm(): IEmplPositionReportingStruct {
    return {
      ...new EmplPositionReportingStruct(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      thruDate: this.editForm.get(['thruDate'])!.value,
      comments: this.editForm.get(['comments'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmplPositionReportingStruct>>): void {
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
}
