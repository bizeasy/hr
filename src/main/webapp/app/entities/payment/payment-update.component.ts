import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPayment, Payment } from 'app/shared/model/payment.model';
import { PaymentService } from './payment.service';
import { IPaymentType } from 'app/shared/model/payment-type.model';
import { PaymentTypeService } from 'app/entities/payment-type/payment-type.service';
import { IPaymentMethodType } from 'app/shared/model/payment-method-type.model';
import { PaymentMethodTypeService } from 'app/entities/payment-method-type/payment-method-type.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';
import { IPaymentMethod } from 'app/shared/model/payment-method.model';
import { PaymentMethodService } from 'app/entities/payment-method/payment-method.service';
import { IPaymentGatewayResponse } from 'app/shared/model/payment-gateway-response.model';
import { PaymentGatewayResponseService } from 'app/entities/payment-gateway-response/payment-gateway-response.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IRoleType } from 'app/shared/model/role-type.model';
import { RoleTypeService } from 'app/entities/role-type/role-type.service';
import { IUom } from 'app/shared/model/uom.model';
import { UomService } from 'app/entities/uom/uom.service';

type SelectableEntity = IPaymentType | IPaymentMethodType | IStatus | IPaymentMethod | IPaymentGatewayResponse | IParty | IRoleType | IUom;

@Component({
  selector: 'sys-payment-update',
  templateUrl: './payment-update.component.html',
})
export class PaymentUpdateComponent implements OnInit {
  isSaving = false;
  paymenttypes: IPaymentType[] = [];
  paymentmethodtypes: IPaymentMethodType[] = [];
  statuses: IStatus[] = [];
  paymentmethods: IPaymentMethod[] = [];
  paymentgatewayresponses: IPaymentGatewayResponse[] = [];
  parties: IParty[] = [];
  roletypes: IRoleType[] = [];
  uoms: IUom[] = [];

  editForm = this.fb.group({
    id: [],
    effectiveDate: [],
    paymentDate: [],
    paymentRefNumber: [],
    amount: [],
    paymentStatus: [],
    mihpayId: [],
    email: [],
    phone: [],
    productInfo: [],
    txnId: [],
    actualAmount: [],
    paymentType: [],
    paymentMethodType: [],
    status: [],
    paymentMethod: [],
    paymentGatewayResponse: [],
    partyIdFrom: [],
    partyIdTo: [],
    roleType: [],
    currencyUom: [],
  });

  constructor(
    protected paymentService: PaymentService,
    protected paymentTypeService: PaymentTypeService,
    protected paymentMethodTypeService: PaymentMethodTypeService,
    protected statusService: StatusService,
    protected paymentMethodService: PaymentMethodService,
    protected paymentGatewayResponseService: PaymentGatewayResponseService,
    protected partyService: PartyService,
    protected roleTypeService: RoleTypeService,
    protected uomService: UomService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payment }) => {
      if (!payment.id) {
        const today = moment().startOf('day');
        payment.effectiveDate = today;
        payment.paymentDate = today;
      }

      this.updateForm(payment);

      this.paymentTypeService.query().subscribe((res: HttpResponse<IPaymentType[]>) => (this.paymenttypes = res.body || []));

      this.paymentMethodTypeService
        .query()
        .subscribe((res: HttpResponse<IPaymentMethodType[]>) => (this.paymentmethodtypes = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));

      this.paymentMethodService.query().subscribe((res: HttpResponse<IPaymentMethod[]>) => (this.paymentmethods = res.body || []));

      this.paymentGatewayResponseService
        .query()
        .subscribe((res: HttpResponse<IPaymentGatewayResponse[]>) => (this.paymentgatewayresponses = res.body || []));

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.roleTypeService.query().subscribe((res: HttpResponse<IRoleType[]>) => (this.roletypes = res.body || []));

      this.uomService.query().subscribe((res: HttpResponse<IUom[]>) => (this.uoms = res.body || []));
    });
  }

  updateForm(payment: IPayment): void {
    this.editForm.patchValue({
      id: payment.id,
      effectiveDate: payment.effectiveDate ? payment.effectiveDate.format(DATE_TIME_FORMAT) : null,
      paymentDate: payment.paymentDate ? payment.paymentDate.format(DATE_TIME_FORMAT) : null,
      paymentRefNumber: payment.paymentRefNumber,
      amount: payment.amount,
      paymentStatus: payment.paymentStatus,
      mihpayId: payment.mihpayId,
      email: payment.email,
      phone: payment.phone,
      productInfo: payment.productInfo,
      txnId: payment.txnId,
      actualAmount: payment.actualAmount,
      paymentType: payment.paymentType,
      paymentMethodType: payment.paymentMethodType,
      status: payment.status,
      paymentMethod: payment.paymentMethod,
      paymentGatewayResponse: payment.paymentGatewayResponse,
      partyIdFrom: payment.partyIdFrom,
      partyIdTo: payment.partyIdTo,
      roleType: payment.roleType,
      currencyUom: payment.currencyUom,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const payment = this.createFromForm();
    if (payment.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentService.update(payment));
    } else {
      this.subscribeToSaveResponse(this.paymentService.create(payment));
    }
  }

  private createFromForm(): IPayment {
    return {
      ...new Payment(),
      id: this.editForm.get(['id'])!.value,
      effectiveDate: this.editForm.get(['effectiveDate'])!.value
        ? moment(this.editForm.get(['effectiveDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      paymentDate: this.editForm.get(['paymentDate'])!.value
        ? moment(this.editForm.get(['paymentDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      paymentRefNumber: this.editForm.get(['paymentRefNumber'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      paymentStatus: this.editForm.get(['paymentStatus'])!.value,
      mihpayId: this.editForm.get(['mihpayId'])!.value,
      email: this.editForm.get(['email'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      productInfo: this.editForm.get(['productInfo'])!.value,
      txnId: this.editForm.get(['txnId'])!.value,
      actualAmount: this.editForm.get(['actualAmount'])!.value,
      paymentType: this.editForm.get(['paymentType'])!.value,
      paymentMethodType: this.editForm.get(['paymentMethodType'])!.value,
      status: this.editForm.get(['status'])!.value,
      paymentMethod: this.editForm.get(['paymentMethod'])!.value,
      paymentGatewayResponse: this.editForm.get(['paymentGatewayResponse'])!.value,
      partyIdFrom: this.editForm.get(['partyIdFrom'])!.value,
      partyIdTo: this.editForm.get(['partyIdTo'])!.value,
      roleType: this.editForm.get(['roleType'])!.value,
      currencyUom: this.editForm.get(['currencyUom'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayment>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
