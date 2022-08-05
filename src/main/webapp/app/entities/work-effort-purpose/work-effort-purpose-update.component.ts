import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorkEffortPurpose, WorkEffortPurpose } from 'app/shared/model/work-effort-purpose.model';
import { WorkEffortPurposeService } from './work-effort-purpose.service';

@Component({
  selector: 'sys-work-effort-purpose-update',
  templateUrl: './work-effort-purpose-update.component.html',
})
export class WorkEffortPurposeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(100)]],
  });

  constructor(
    protected workEffortPurposeService: WorkEffortPurposeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffortPurpose }) => {
      this.updateForm(workEffortPurpose);
    });
  }

  updateForm(workEffortPurpose: IWorkEffortPurpose): void {
    this.editForm.patchValue({
      id: workEffortPurpose.id,
      name: workEffortPurpose.name,
      description: workEffortPurpose.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workEffortPurpose = this.createFromForm();
    if (workEffortPurpose.id !== undefined) {
      this.subscribeToSaveResponse(this.workEffortPurposeService.update(workEffortPurpose));
    } else {
      this.subscribeToSaveResponse(this.workEffortPurposeService.create(workEffortPurpose));
    }
  }

  private createFromForm(): IWorkEffortPurpose {
    return {
      ...new WorkEffortPurpose(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkEffortPurpose>>): void {
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
