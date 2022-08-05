import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorkEffortAssocType, WorkEffortAssocType } from 'app/shared/model/work-effort-assoc-type.model';
import { WorkEffortAssocTypeService } from './work-effort-assoc-type.service';

@Component({
  selector: 'sys-work-effort-assoc-type-update',
  templateUrl: './work-effort-assoc-type-update.component.html',
})
export class WorkEffortAssocTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(100)]],
  });

  constructor(
    protected workEffortAssocTypeService: WorkEffortAssocTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffortAssocType }) => {
      this.updateForm(workEffortAssocType);
    });
  }

  updateForm(workEffortAssocType: IWorkEffortAssocType): void {
    this.editForm.patchValue({
      id: workEffortAssocType.id,
      name: workEffortAssocType.name,
      description: workEffortAssocType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workEffortAssocType = this.createFromForm();
    if (workEffortAssocType.id !== undefined) {
      this.subscribeToSaveResponse(this.workEffortAssocTypeService.update(workEffortAssocType));
    } else {
      this.subscribeToSaveResponse(this.workEffortAssocTypeService.create(workEffortAssocType));
    }
  }

  private createFromForm(): IWorkEffortAssocType {
    return {
      ...new WorkEffortAssocType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkEffortAssocType>>): void {
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
