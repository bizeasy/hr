import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrderItem, OrderItem } from 'app/shared/model/order-item.model';
import { OrderItemService } from './order-item.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';
import { IOrderItemType } from 'app/shared/model/order-item-type.model';
import { OrderItemTypeService } from 'app/entities/order-item-type/order-item-type.service';
import { IInventoryItem } from 'app/shared/model/inventory-item.model';
import { InventoryItemService } from 'app/entities/inventory-item/inventory-item.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';
import { ISupplierProduct } from 'app/shared/model/supplier-product.model';
import { SupplierProductService } from 'app/entities/supplier-product/supplier-product.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';
import { ITaxAuthorityRateType } from 'app/shared/model/tax-authority-rate-type.model';
import { TaxAuthorityRateTypeService } from 'app/entities/tax-authority-rate-type/tax-authority-rate-type.service';

type SelectableEntity = IOrder | IOrderItemType | IInventoryItem | IProduct | ISupplierProduct | IStatus | ITaxAuthorityRateType;

@Component({
  selector: 'sys-order-item-update',
  templateUrl: './order-item-update.component.html',
})
export class OrderItemUpdateComponent implements OnInit {
  isSaving = false;
  orders: IOrder[] = [];
  orderitemtypes: IOrderItemType[] = [];
  inventoryitems: IInventoryItem[] = [];
  products: IProduct[] = [];
  supplierproducts: ISupplierProduct[] = [];
  statuses: IStatus[] = [];
  taxauthorityratetypes: ITaxAuthorityRateType[] = [];

  editForm = this.fb.group({
    id: [],
    sequenceNo: [],
    quantity: [],
    cancelQuantity: [],
    selectedAmount: [],
    unitPrice: [],
    unitListPrice: [],
    cgst: [],
    igst: [],
    sgst: [],
    cgstPercentage: [],
    igstPercentage: [],
    sgstPercentage: [],
    totalPrice: [],
    isModifiedPrice: [],
    estimatedShipDate: [],
    estimatedDeliveryDate: [],
    shipBeforeDate: [],
    shipAfterDate: [],
    order: [],
    orderItemType: [],
    fromInventoryItem: [],
    product: [],
    supplierProduct: [],
    status: [],
    taxAuthRate: [],
  });

  constructor(
    protected orderItemService: OrderItemService,
    protected orderService: OrderService,
    protected orderItemTypeService: OrderItemTypeService,
    protected inventoryItemService: InventoryItemService,
    protected productService: ProductService,
    protected supplierProductService: SupplierProductService,
    protected statusService: StatusService,
    protected taxAuthorityRateTypeService: TaxAuthorityRateTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderItem }) => {
      if (!orderItem.id) {
        const today = moment().startOf('day');
        orderItem.estimatedShipDate = today;
        orderItem.estimatedDeliveryDate = today;
        orderItem.shipBeforeDate = today;
        orderItem.shipAfterDate = today;
      }

      this.updateForm(orderItem);

      this.orderService.query().subscribe((res: HttpResponse<IOrder[]>) => (this.orders = res.body || []));

      this.orderItemTypeService.query().subscribe((res: HttpResponse<IOrderItemType[]>) => (this.orderitemtypes = res.body || []));

      this.inventoryItemService.query().subscribe((res: HttpResponse<IInventoryItem[]>) => (this.inventoryitems = res.body || []));

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.supplierProductService.query().subscribe((res: HttpResponse<ISupplierProduct[]>) => (this.supplierproducts = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));

      this.taxAuthorityRateTypeService
        .query()
        .subscribe((res: HttpResponse<ITaxAuthorityRateType[]>) => (this.taxauthorityratetypes = res.body || []));
    });
  }

  updateForm(orderItem: IOrderItem): void {
    this.editForm.patchValue({
      id: orderItem.id,
      sequenceNo: orderItem.sequenceNo,
      quantity: orderItem.quantity,
      cancelQuantity: orderItem.cancelQuantity,
      selectedAmount: orderItem.selectedAmount,
      unitPrice: orderItem.unitPrice,
      unitListPrice: orderItem.unitListPrice,
      cgst: orderItem.cgst,
      igst: orderItem.igst,
      sgst: orderItem.sgst,
      cgstPercentage: orderItem.cgstPercentage,
      igstPercentage: orderItem.igstPercentage,
      sgstPercentage: orderItem.sgstPercentage,
      totalPrice: orderItem.totalPrice,
      isModifiedPrice: orderItem.isModifiedPrice,
      estimatedShipDate: orderItem.estimatedShipDate ? orderItem.estimatedShipDate.format(DATE_TIME_FORMAT) : null,
      estimatedDeliveryDate: orderItem.estimatedDeliveryDate ? orderItem.estimatedDeliveryDate.format(DATE_TIME_FORMAT) : null,
      shipBeforeDate: orderItem.shipBeforeDate ? orderItem.shipBeforeDate.format(DATE_TIME_FORMAT) : null,
      shipAfterDate: orderItem.shipAfterDate ? orderItem.shipAfterDate.format(DATE_TIME_FORMAT) : null,
      order: orderItem.order,
      orderItemType: orderItem.orderItemType,
      fromInventoryItem: orderItem.fromInventoryItem,
      product: orderItem.product,
      supplierProduct: orderItem.supplierProduct,
      status: orderItem.status,
      taxAuthRate: orderItem.taxAuthRate,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderItem = this.createFromForm();
    if (orderItem.id !== undefined) {
      this.subscribeToSaveResponse(this.orderItemService.update(orderItem));
    } else {
      this.subscribeToSaveResponse(this.orderItemService.create(orderItem));
    }
  }

  private createFromForm(): IOrderItem {
    return {
      ...new OrderItem(),
      id: this.editForm.get(['id'])!.value,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      cancelQuantity: this.editForm.get(['cancelQuantity'])!.value,
      selectedAmount: this.editForm.get(['selectedAmount'])!.value,
      unitPrice: this.editForm.get(['unitPrice'])!.value,
      unitListPrice: this.editForm.get(['unitListPrice'])!.value,
      cgst: this.editForm.get(['cgst'])!.value,
      igst: this.editForm.get(['igst'])!.value,
      sgst: this.editForm.get(['sgst'])!.value,
      cgstPercentage: this.editForm.get(['cgstPercentage'])!.value,
      igstPercentage: this.editForm.get(['igstPercentage'])!.value,
      sgstPercentage: this.editForm.get(['sgstPercentage'])!.value,
      totalPrice: this.editForm.get(['totalPrice'])!.value,
      isModifiedPrice: this.editForm.get(['isModifiedPrice'])!.value,
      estimatedShipDate: this.editForm.get(['estimatedShipDate'])!.value
        ? moment(this.editForm.get(['estimatedShipDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      estimatedDeliveryDate: this.editForm.get(['estimatedDeliveryDate'])!.value
        ? moment(this.editForm.get(['estimatedDeliveryDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      shipBeforeDate: this.editForm.get(['shipBeforeDate'])!.value
        ? moment(this.editForm.get(['shipBeforeDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      shipAfterDate: this.editForm.get(['shipAfterDate'])!.value
        ? moment(this.editForm.get(['shipAfterDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      order: this.editForm.get(['order'])!.value,
      orderItemType: this.editForm.get(['orderItemType'])!.value,
      fromInventoryItem: this.editForm.get(['fromInventoryItem'])!.value,
      product: this.editForm.get(['product'])!.value,
      supplierProduct: this.editForm.get(['supplierProduct'])!.value,
      status: this.editForm.get(['status'])!.value,
      taxAuthRate: this.editForm.get(['taxAuthRate'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderItem>>): void {
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
