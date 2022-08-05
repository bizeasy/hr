import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPaymentMethodType, PaymentMethodType } from 'app/shared/model/payment-method-type.model';
import { PaymentMethodTypeService } from './payment-method-type.service';

@Component({
  selector: 'sys-payment-method-type-update',
  templateUrl: './payment-method-type-update.component.html',
})
export class PaymentMethodTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(60)]],
  });

  constructor(
    protected paymentMethodTypeService: PaymentMethodTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentMethodType }) => {
      this.updateForm(paymentMethodType);
    });
  }

  updateForm(paymentMethodType: IPaymentMethodType): void {
    this.editForm.patchValue({
      id: paymentMethodType.id,
      name: paymentMethodType.name,
      description: paymentMethodType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentMethodType = this.createFromForm();
    if (paymentMethodType.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentMethodTypeService.update(paymentMethodType));
    } else {
      this.subscribeToSaveResponse(this.paymentMethodTypeService.create(paymentMethodType));
    }
  }

  private createFromForm(): IPaymentMethodType {
    return {
      ...new PaymentMethodType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentMethodType>>): void {
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
