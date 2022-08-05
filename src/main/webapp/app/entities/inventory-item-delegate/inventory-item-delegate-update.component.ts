import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInventoryItemDelegate, InventoryItemDelegate } from 'app/shared/model/inventory-item-delegate.model';
import { InventoryItemDelegateService } from './inventory-item-delegate.service';
import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from 'app/entities/invoice/invoice.service';
import { IInvoiceItem } from 'app/shared/model/invoice-item.model';
import { InvoiceItemService } from 'app/entities/invoice-item/invoice-item.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { OrderItemService } from 'app/entities/order-item/order-item.service';
import { IItemIssuance } from 'app/shared/model/item-issuance.model';
import { ItemIssuanceService } from 'app/entities/item-issuance/item-issuance.service';
import { IInventoryTransfer } from 'app/shared/model/inventory-transfer.model';
import { InventoryTransferService } from 'app/entities/inventory-transfer/inventory-transfer.service';
import { IInventoryItemVariance } from 'app/shared/model/inventory-item-variance.model';
import { InventoryItemVarianceService } from 'app/entities/inventory-item-variance/inventory-item-variance.service';
import { IInventoryItem } from 'app/shared/model/inventory-item.model';
import { InventoryItemService } from 'app/entities/inventory-item/inventory-item.service';

type SelectableEntity =
  | IInvoice
  | IInvoiceItem
  | IOrder
  | IOrderItem
  | IItemIssuance
  | IInventoryTransfer
  | IInventoryItemVariance
  | IInventoryItem;

@Component({
  selector: 'sys-inventory-item-delegate-update',
  templateUrl: './inventory-item-delegate-update.component.html',
})
export class InventoryItemDelegateUpdateComponent implements OnInit {
  isSaving = false;
  invoices: IInvoice[] = [];
  invoiceitems: IInvoiceItem[] = [];
  orders: IOrder[] = [];
  orderitems: IOrderItem[] = [];
  itemissuances: IItemIssuance[] = [];
  inventorytransfers: IInventoryTransfer[] = [];
  inventoryitemvariances: IInventoryItemVariance[] = [];
  inventoryitems: IInventoryItem[] = [];

  editForm = this.fb.group({
    id: [],
    sequenceNo: [],
    effectiveDate: [],
    quantityOnHandDiff: [],
    availableToPromiseDiff: [],
    accountingQuantityDiff: [],
    unitCost: [],
    remarks: [null, [Validators.maxLength(255)]],
    invoice: [],
    invoiceItem: [],
    order: [],
    orderItem: [],
    itemIssuance: [],
    inventoryTransfer: [],
    inventoryItemVariance: [],
    inventoryItem: [],
  });

  constructor(
    protected inventoryItemDelegateService: InventoryItemDelegateService,
    protected invoiceService: InvoiceService,
    protected invoiceItemService: InvoiceItemService,
    protected orderService: OrderService,
    protected orderItemService: OrderItemService,
    protected itemIssuanceService: ItemIssuanceService,
    protected inventoryTransferService: InventoryTransferService,
    protected inventoryItemVarianceService: InventoryItemVarianceService,
    protected inventoryItemService: InventoryItemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryItemDelegate }) => {
      if (!inventoryItemDelegate.id) {
        const today = moment().startOf('day');
        inventoryItemDelegate.effectiveDate = today;
      }

      this.updateForm(inventoryItemDelegate);

      this.invoiceService.query().subscribe((res: HttpResponse<IInvoice[]>) => (this.invoices = res.body || []));

      this.invoiceItemService.query().subscribe((res: HttpResponse<IInvoiceItem[]>) => (this.invoiceitems = res.body || []));

      this.orderService.query().subscribe((res: HttpResponse<IOrder[]>) => (this.orders = res.body || []));

      this.orderItemService.query().subscribe((res: HttpResponse<IOrderItem[]>) => (this.orderitems = res.body || []));

      this.itemIssuanceService.query().subscribe((res: HttpResponse<IItemIssuance[]>) => (this.itemissuances = res.body || []));

      this.inventoryTransferService
        .query()
        .subscribe((res: HttpResponse<IInventoryTransfer[]>) => (this.inventorytransfers = res.body || []));

      this.inventoryItemVarianceService
        .query()
        .subscribe((res: HttpResponse<IInventoryItemVariance[]>) => (this.inventoryitemvariances = res.body || []));

      this.inventoryItemService.query().subscribe((res: HttpResponse<IInventoryItem[]>) => (this.inventoryitems = res.body || []));
    });
  }

  updateForm(inventoryItemDelegate: IInventoryItemDelegate): void {
    this.editForm.patchValue({
      id: inventoryItemDelegate.id,
      sequenceNo: inventoryItemDelegate.sequenceNo,
      effectiveDate: inventoryItemDelegate.effectiveDate ? inventoryItemDelegate.effectiveDate.format(DATE_TIME_FORMAT) : null,
      quantityOnHandDiff: inventoryItemDelegate.quantityOnHandDiff,
      availableToPromiseDiff: inventoryItemDelegate.availableToPromiseDiff,
      accountingQuantityDiff: inventoryItemDelegate.accountingQuantityDiff,
      unitCost: inventoryItemDelegate.unitCost,
      remarks: inventoryItemDelegate.remarks,
      invoice: inventoryItemDelegate.invoice,
      invoiceItem: inventoryItemDelegate.invoiceItem,
      order: inventoryItemDelegate.order,
      orderItem: inventoryItemDelegate.orderItem,
      itemIssuance: inventoryItemDelegate.itemIssuance,
      inventoryTransfer: inventoryItemDelegate.inventoryTransfer,
      inventoryItemVariance: inventoryItemDelegate.inventoryItemVariance,
      inventoryItem: inventoryItemDelegate.inventoryItem,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventoryItemDelegate = this.createFromForm();
    if (inventoryItemDelegate.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryItemDelegateService.update(inventoryItemDelegate));
    } else {
      this.subscribeToSaveResponse(this.inventoryItemDelegateService.create(inventoryItemDelegate));
    }
  }

  private createFromForm(): IInventoryItemDelegate {
    return {
      ...new InventoryItemDelegate(),
      id: this.editForm.get(['id'])!.value,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      effectiveDate: this.editForm.get(['effectiveDate'])!.value
        ? moment(this.editForm.get(['effectiveDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      quantityOnHandDiff: this.editForm.get(['quantityOnHandDiff'])!.value,
      availableToPromiseDiff: this.editForm.get(['availableToPromiseDiff'])!.value,
      accountingQuantityDiff: this.editForm.get(['accountingQuantityDiff'])!.value,
      unitCost: this.editForm.get(['unitCost'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      invoice: this.editForm.get(['invoice'])!.value,
      invoiceItem: this.editForm.get(['invoiceItem'])!.value,
      order: this.editForm.get(['order'])!.value,
      orderItem: this.editForm.get(['orderItem'])!.value,
      itemIssuance: this.editForm.get(['itemIssuance'])!.value,
      inventoryTransfer: this.editForm.get(['inventoryTransfer'])!.value,
      inventoryItemVariance: this.editForm.get(['inventoryItemVariance'])!.value,
      inventoryItem: this.editForm.get(['inventoryItem'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryItemDelegate>>): void {
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
