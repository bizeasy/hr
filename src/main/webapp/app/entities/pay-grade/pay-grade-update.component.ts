import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPayGrade, PayGrade } from 'app/shared/model/pay-grade.model';
import { PayGradeService } from './pay-grade.service';

@Component({
  selector: 'sys-pay-grade-update',
  templateUrl: './pay-grade-update.component.html',
})
export class PayGradeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [],
  });

  constructor(protected payGradeService: PayGradeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payGrade }) => {
      this.updateForm(payGrade);
    });
  }

  updateForm(payGrade: IPayGrade): void {
    this.editForm.patchValue({
      id: payGrade.id,
      name: payGrade.name,
      description: payGrade.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const payGrade = this.createFromForm();
    if (payGrade.id !== undefined) {
      this.subscribeToSaveResponse(this.payGradeService.update(payGrade));
    } else {
      this.subscribeToSaveResponse(this.payGradeService.create(payGrade));
    }
  }

  private createFromForm(): IPayGrade {
    return {
      ...new PayGrade(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayGrade>>): void {
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
