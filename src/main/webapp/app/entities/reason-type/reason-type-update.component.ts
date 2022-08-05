import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IReasonType, ReasonType } from 'app/shared/model/reason-type.model';
import { ReasonTypeService } from './reason-type.service';

@Component({
  selector: 'sys-reason-type-update',
  templateUrl: './reason-type-update.component.html',
})
export class ReasonTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(100)]],
  });

  constructor(protected reasonTypeService: ReasonTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reasonType }) => {
      this.updateForm(reasonType);
    });
  }

  updateForm(reasonType: IReasonType): void {
    this.editForm.patchValue({
      id: reasonType.id,
      name: reasonType.name,
      description: reasonType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reasonType = this.createFromForm();
    if (reasonType.id !== undefined) {
      this.subscribeToSaveResponse(this.reasonTypeService.update(reasonType));
    } else {
      this.subscribeToSaveResponse(this.reasonTypeService.create(reasonType));
    }
  }

  private createFromForm(): IReasonType {
    return {
      ...new ReasonType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReasonType>>): void {
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
