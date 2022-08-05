import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDeduction, Deduction } from 'app/shared/model/deduction.model';
import { DeductionService } from './deduction.service';
import { IDeductionType } from 'app/shared/model/deduction-type.model';
import { DeductionTypeService } from 'app/entities/deduction-type/deduction-type.service';

@Component({
  selector: 'sys-deduction-update',
  templateUrl: './deduction-update.component.html',
})
export class DeductionUpdateComponent implements OnInit {
  isSaving = false;
  deductiontypes: IDeductionType[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [],
    type: [],
  });

  constructor(
    protected deductionService: DeductionService,
    protected deductionTypeService: DeductionTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deduction }) => {
      this.updateForm(deduction);

      this.deductionTypeService.query().subscribe((res: HttpResponse<IDeductionType[]>) => (this.deductiontypes = res.body || []));
    });
  }

  updateForm(deduction: IDeduction): void {
    this.editForm.patchValue({
      id: deduction.id,
      amount: deduction.amount,
      type: deduction.type,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deduction = this.createFromForm();
    if (deduction.id !== undefined) {
      this.subscribeToSaveResponse(this.deductionService.update(deduction));
    } else {
      this.subscribeToSaveResponse(this.deductionService.create(deduction));
    }
  }

  private createFromForm(): IDeduction {
    return {
      ...new Deduction(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      type: this.editForm.get(['type'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeduction>>): void {
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

  trackById(index: number, item: IDeductionType): any {
    return item.id;
  }
}
