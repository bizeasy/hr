import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmplLeaveReasonType, EmplLeaveReasonType } from 'app/shared/model/empl-leave-reason-type.model';
import { EmplLeaveReasonTypeService } from './empl-leave-reason-type.service';

@Component({
  selector: 'sys-empl-leave-reason-type-update',
  templateUrl: './empl-leave-reason-type-update.component.html',
})
export class EmplLeaveReasonTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [],
  });

  constructor(
    protected emplLeaveReasonTypeService: EmplLeaveReasonTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplLeaveReasonType }) => {
      this.updateForm(emplLeaveReasonType);
    });
  }

  updateForm(emplLeaveReasonType: IEmplLeaveReasonType): void {
    this.editForm.patchValue({
      id: emplLeaveReasonType.id,
      name: emplLeaveReasonType.name,
      description: emplLeaveReasonType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emplLeaveReasonType = this.createFromForm();
    if (emplLeaveReasonType.id !== undefined) {
      this.subscribeToSaveResponse(this.emplLeaveReasonTypeService.update(emplLeaveReasonType));
    } else {
      this.subscribeToSaveResponse(this.emplLeaveReasonTypeService.create(emplLeaveReasonType));
    }
  }

  private createFromForm(): IEmplLeaveReasonType {
    return {
      ...new EmplLeaveReasonType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmplLeaveReasonType>>): void {
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
