import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDeductionType, DeductionType } from 'app/shared/model/deduction-type.model';
import { DeductionTypeService } from './deduction-type.service';

@Component({
  selector: 'sys-deduction-type-update',
  templateUrl: './deduction-type-update.component.html',
})
export class DeductionTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [],
  });

  constructor(protected deductionTypeService: DeductionTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deductionType }) => {
      this.updateForm(deductionType);
    });
  }

  updateForm(deductionType: IDeductionType): void {
    this.editForm.patchValue({
      id: deductionType.id,
      name: deductionType.name,
      description: deductionType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deductionType = this.createFromForm();
    if (deductionType.id !== undefined) {
      this.subscribeToSaveResponse(this.deductionTypeService.update(deductionType));
    } else {
      this.subscribeToSaveResponse(this.deductionTypeService.create(deductionType));
    }
  }

  private createFromForm(): IDeductionType {
    return {
      ...new DeductionType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeductionType>>): void {
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
