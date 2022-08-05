import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPaymentGatewayConfig, PaymentGatewayConfig } from 'app/shared/model/payment-gateway-config.model';
import { PaymentGatewayConfigService } from './payment-gateway-config.service';

@Component({
  selector: 'sys-payment-gateway-config-update',
  templateUrl: './payment-gateway-config-update.component.html',
})
export class PaymentGatewayConfigUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    authUrl: [],
    releaseUrl: [],
    refundUrl: [],
  });

  constructor(
    protected paymentGatewayConfigService: PaymentGatewayConfigService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentGatewayConfig }) => {
      this.updateForm(paymentGatewayConfig);
    });
  }

  updateForm(paymentGatewayConfig: IPaymentGatewayConfig): void {
    this.editForm.patchValue({
      id: paymentGatewayConfig.id,
      name: paymentGatewayConfig.name,
      authUrl: paymentGatewayConfig.authUrl,
      releaseUrl: paymentGatewayConfig.releaseUrl,
      refundUrl: paymentGatewayConfig.refundUrl,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentGatewayConfig = this.createFromForm();
    if (paymentGatewayConfig.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentGatewayConfigService.update(paymentGatewayConfig));
    } else {
      this.subscribeToSaveResponse(this.paymentGatewayConfigService.create(paymentGatewayConfig));
    }
  }

  private createFromForm(): IPaymentGatewayConfig {
    return {
      ...new PaymentGatewayConfig(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      authUrl: this.editForm.get(['authUrl'])!.value,
      releaseUrl: this.editForm.get(['releaseUrl'])!.value,
      refundUrl: this.editForm.get(['refundUrl'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentGatewayConfig>>): void {
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
