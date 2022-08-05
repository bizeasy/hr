import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmplLeaveType, EmplLeaveType } from 'app/shared/model/empl-leave-type.model';
import { EmplLeaveTypeService } from './empl-leave-type.service';

@Component({
  selector: 'sys-empl-leave-type-update',
  templateUrl: './empl-leave-type-update.component.html',
})
export class EmplLeaveTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [],
  });

  constructor(protected emplLeaveTypeService: EmplLeaveTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplLeaveType }) => {
      this.updateForm(emplLeaveType);
    });
  }

  updateForm(emplLeaveType: IEmplLeaveType): void {
    this.editForm.patchValue({
      id: emplLeaveType.id,
      name: emplLeaveType.name,
      description: emplLeaveType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emplLeaveType = this.createFromForm();
    if (emplLeaveType.id !== undefined) {
      this.subscribeToSaveResponse(this.emplLeaveTypeService.update(emplLeaveType));
    } else {
      this.subscribeToSaveResponse(this.emplLeaveTypeService.create(emplLeaveType));
    }
  }

  private createFromForm(): IEmplLeaveType {
    return {
      ...new EmplLeaveType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmplLeaveType>>): void {
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
