import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPaymentApplication, PaymentApplication } from 'app/shared/model/payment-application.model';
import { PaymentApplicationService } from './payment-application.service';
import { IPayment } from 'app/shared/model/payment.model';
import { PaymentService } from 'app/entities/payment/payment.service';
import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from 'app/entities/invoice/invoice.service';
import { IInvoiceItem } from 'app/shared/model/invoice-item.model';
import { InvoiceItemService } from 'app/entities/invoice-item/invoice-item.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { OrderItemService } from 'app/entities/order-item/order-item.service';

type SelectableEntity = IPayment | IInvoice | IInvoiceItem | IOrder | IOrderItem;

@Component({
  selector: 'sys-payment-application-update',
  templateUrl: './payment-application-update.component.html',
})
export class PaymentApplicationUpdateComponent implements OnInit {
  isSaving = false;
  payments: IPayment[] = [];
  invoices: IInvoice[] = [];
  invoiceitems: IInvoiceItem[] = [];
  orders: IOrder[] = [];
  orderitems: IOrderItem[] = [];

  editForm = this.fb.group({
    id: [],
    amountApplied: [],
    payment: [],
    invoice: [],
    invoiceItem: [],
    order: [],
    orderItem: [],
    toPayment: [],
  });

  constructor(
    protected paymentApplicationService: PaymentApplicationService,
    protected paymentService: PaymentService,
    protected invoiceService: InvoiceService,
    protected invoiceItemService: InvoiceItemService,
    protected orderService: OrderService,
    protected orderItemService: OrderItemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentApplication }) => {
      this.updateForm(paymentApplication);

      this.paymentService.query().subscribe((res: HttpResponse<IPayment[]>) => (this.payments = res.body || []));

      this.invoiceService.query().subscribe((res: HttpResponse<IInvoice[]>) => (this.invoices = res.body || []));

      this.invoiceItemService.query().subscribe((res: HttpResponse<IInvoiceItem[]>) => (this.invoiceitems = res.body || []));

      this.orderService.query().subscribe((res: HttpResponse<IOrder[]>) => (this.orders = res.body || []));

      this.orderItemService.query().subscribe((res: HttpResponse<IOrderItem[]>) => (this.orderitems = res.body || []));
    });
  }

  updateForm(paymentApplication: IPaymentApplication): void {
    this.editForm.patchValue({
      id: paymentApplication.id,
      amountApplied: paymentApplication.amountApplied,
      payment: paymentApplication.payment,
      invoice: paymentApplication.invoice,
      invoiceItem: paymentApplication.invoiceItem,
      order: paymentApplication.order,
      orderItem: paymentApplication.orderItem,
      toPayment: paymentApplication.toPayment,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentApplication = this.createFromForm();
    if (paymentApplication.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentApplicationService.update(paymentApplication));
    } else {
      this.subscribeToSaveResponse(this.paymentApplicationService.create(paymentApplication));
    }
  }

  private createFromForm(): IPaymentApplication {
    return {
      ...new PaymentApplication(),
      id: this.editForm.get(['id'])!.value,
      amountApplied: this.editForm.get(['amountApplied'])!.value,
      payment: this.editForm.get(['payment'])!.value,
      invoice: this.editForm.get(['invoice'])!.value,
      invoiceItem: this.editForm.get(['invoiceItem'])!.value,
      order: this.editForm.get(['order'])!.value,
      orderItem: this.editForm.get(['orderItem'])!.value,
      toPayment: this.editForm.get(['toPayment'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentApplication>>): void {
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
