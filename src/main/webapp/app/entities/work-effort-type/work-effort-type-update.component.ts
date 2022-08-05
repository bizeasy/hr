import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorkEffortType, WorkEffortType } from 'app/shared/model/work-effort-type.model';
import { WorkEffortTypeService } from './work-effort-type.service';

@Component({
  selector: 'sys-work-effort-type-update',
  templateUrl: './work-effort-type-update.component.html',
})
export class WorkEffortTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(100)]],
  });

  constructor(protected workEffortTypeService: WorkEffortTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffortType }) => {
      this.updateForm(workEffortType);
    });
  }

  updateForm(workEffortType: IWorkEffortType): void {
    this.editForm.patchValue({
      id: workEffortType.id,
      name: workEffortType.name,
      description: workEffortType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workEffortType = this.createFromForm();
    if (workEffortType.id !== undefined) {
      this.subscribeToSaveResponse(this.workEffortTypeService.update(workEffortType));
    } else {
      this.subscribeToSaveResponse(this.workEffortTypeService.create(workEffortType));
    }
  }

  private createFromForm(): IWorkEffortType {
    return {
      ...new WorkEffortType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkEffortType>>): void {
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
