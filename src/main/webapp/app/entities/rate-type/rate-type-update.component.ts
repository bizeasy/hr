import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRateType, RateType } from 'app/shared/model/rate-type.model';
import { RateTypeService } from './rate-type.service';

@Component({
  selector: 'sys-rate-type-update',
  templateUrl: './rate-type-update.component.html',
})
export class RateTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [],
  });

  constructor(protected rateTypeService: RateTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rateType }) => {
      this.updateForm(rateType);
    });
  }

  updateForm(rateType: IRateType): void {
    this.editForm.patchValue({
      id: rateType.id,
      name: rateType.name,
      description: rateType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rateType = this.createFromForm();
    if (rateType.id !== undefined) {
      this.subscribeToSaveResponse(this.rateTypeService.update(rateType));
    } else {
      this.subscribeToSaveResponse(this.rateTypeService.create(rateType));
    }
  }

  private createFromForm(): IRateType {
    return {
      ...new RateType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRateType>>): void {
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
