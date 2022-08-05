import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOrderItemBilling, OrderItemBilling } from 'app/shared/model/order-item-billing.model';
import { OrderItemBillingService } from './order-item-billing.service';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { OrderItemService } from 'app/entities/order-item/order-item.service';
import { IInvoiceItem } from 'app/shared/model/invoice-item.model';
import { InvoiceItemService } from 'app/entities/invoice-item/invoice-item.service';

type SelectableEntity = IOrderItem | IInvoiceItem;

@Component({
  selector: 'sys-order-item-billing-update',
  templateUrl: './order-item-billing-update.component.html',
})
export class OrderItemBillingUpdateComponent implements OnInit {
  isSaving = false;
  orderitems: IOrderItem[] = [];
  invoiceitems: IInvoiceItem[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [],
    amount: [],
    orderItem: [],
    invoiceItem: [],
  });

  constructor(
    protected orderItemBillingService: OrderItemBillingService,
    protected orderItemService: OrderItemService,
    protected invoiceItemService: InvoiceItemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderItemBilling }) => {
      this.updateForm(orderItemBilling);

      this.orderItemService.query().subscribe((res: HttpResponse<IOrderItem[]>) => (this.orderitems = res.body || []));

      this.invoiceItemService.query().subscribe((res: HttpResponse<IInvoiceItem[]>) => (this.invoiceitems = res.body || []));
    });
  }

  updateForm(orderItemBilling: IOrderItemBilling): void {
    this.editForm.patchValue({
      id: orderItemBilling.id,
      quantity: orderItemBilling.quantity,
      amount: orderItemBilling.amount,
      orderItem: orderItemBilling.orderItem,
      invoiceItem: orderItemBilling.invoiceItem,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderItemBilling = this.createFromForm();
    if (orderItemBilling.id !== undefined) {
      this.subscribeToSaveResponse(this.orderItemBillingService.update(orderItemBilling));
    } else {
      this.subscribeToSaveResponse(this.orderItemBillingService.create(orderItemBilling));
    }
  }

  private createFromForm(): IOrderItemBilling {
    return {
      ...new OrderItemBilling(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      orderItem: this.editForm.get(['orderItem'])!.value,
      invoiceItem: this.editForm.get(['invoiceItem'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderItemBilling>>): void {
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
