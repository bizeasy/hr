import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPaymentGatewayResponse, PaymentGatewayResponse } from 'app/shared/model/payment-gateway-response.model';
import { PaymentGatewayResponseService } from './payment-gateway-response.service';
import { IPaymentMethodType } from 'app/shared/model/payment-method-type.model';
import { PaymentMethodTypeService } from 'app/entities/payment-method-type/payment-method-type.service';

@Component({
  selector: 'sys-payment-gateway-response-update',
  templateUrl: './payment-gateway-response-update.component.html',
})
export class PaymentGatewayResponseUpdateComponent implements OnInit {
  isSaving = false;
  paymentmethodtypes: IPaymentMethodType[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [],
    referenceNumber: [null, [Validators.maxLength(25)]],
    gatewayMessage: [],
    paymentMethodType: [],
  });

  constructor(
    protected paymentGatewayResponseService: PaymentGatewayResponseService,
    protected paymentMethodTypeService: PaymentMethodTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentGatewayResponse }) => {
      this.updateForm(paymentGatewayResponse);

      this.paymentMethodTypeService
        .query()
        .subscribe((res: HttpResponse<IPaymentMethodType[]>) => (this.paymentmethodtypes = res.body || []));
    });
  }

  updateForm(paymentGatewayResponse: IPaymentGatewayResponse): void {
    this.editForm.patchValue({
      id: paymentGatewayResponse.id,
      amount: paymentGatewayResponse.amount,
      referenceNumber: paymentGatewayResponse.referenceNumber,
      gatewayMessage: paymentGatewayResponse.gatewayMessage,
      paymentMethodType: paymentGatewayResponse.paymentMethodType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentGatewayResponse = this.createFromForm();
    if (paymentGatewayResponse.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentGatewayResponseService.update(paymentGatewayResponse));
    } else {
      this.subscribeToSaveResponse(this.paymentGatewayResponseService.create(paymentGatewayResponse));
    }
  }

  private createFromForm(): IPaymentGatewayResponse {
    return {
      ...new PaymentGatewayResponse(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      referenceNumber: this.editForm.get(['referenceNumber'])!.value,
      gatewayMessage: this.editForm.get(['gatewayMessage'])!.value,
      paymentMethodType: this.editForm.get(['paymentMethodType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentGatewayResponse>>): void {
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

  trackById(index: number, item: IPaymentMethodType): any {
    return item.id;
  }
}
