import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInvoiceItem, InvoiceItem } from 'app/shared/model/invoice-item.model';
import { InvoiceItemService } from './invoice-item.service';
import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from 'app/entities/invoice/invoice.service';
import { IInvoiceItemType } from 'app/shared/model/invoice-item-type.model';
import { InvoiceItemTypeService } from 'app/entities/invoice-item-type/invoice-item-type.service';
import { IInventoryItem } from 'app/shared/model/inventory-item.model';
import { InventoryItemService } from 'app/entities/inventory-item/inventory-item.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';
import { ITaxAuthorityRateType } from 'app/shared/model/tax-authority-rate-type.model';
import { TaxAuthorityRateTypeService } from 'app/entities/tax-authority-rate-type/tax-authority-rate-type.service';

type SelectableEntity = IInvoice | IInvoiceItemType | IInventoryItem | IProduct | ITaxAuthorityRateType;

@Component({
  selector: 'sys-invoice-item-update',
  templateUrl: './invoice-item-update.component.html',
})
export class InvoiceItemUpdateComponent implements OnInit {
  isSaving = false;
  invoices: IInvoice[] = [];
  invoiceitemtypes: IInvoiceItemType[] = [];
  inventoryitems: IInventoryItem[] = [];
  products: IProduct[] = [];
  taxauthorityratetypes: ITaxAuthorityRateType[] = [];

  editForm = this.fb.group({
    id: [],
    sequenceNo: [],
    quantity: [],
    amount: [],
    invoice: [],
    invoiceItemType: [],
    fromInventoryItem: [],
    product: [],
    taxAuthRate: [],
  });

  constructor(
    protected invoiceItemService: InvoiceItemService,
    protected invoiceService: InvoiceService,
    protected invoiceItemTypeService: InvoiceItemTypeService,
    protected inventoryItemService: InventoryItemService,
    protected productService: ProductService,
    protected taxAuthorityRateTypeService: TaxAuthorityRateTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoiceItem }) => {
      this.updateForm(invoiceItem);

      this.invoiceService.query().subscribe((res: HttpResponse<IInvoice[]>) => (this.invoices = res.body || []));

      this.invoiceItemTypeService.query().subscribe((res: HttpResponse<IInvoiceItemType[]>) => (this.invoiceitemtypes = res.body || []));

      this.inventoryItemService.query().subscribe((res: HttpResponse<IInventoryItem[]>) => (this.inventoryitems = res.body || []));

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.taxAuthorityRateTypeService
        .query()
        .subscribe((res: HttpResponse<ITaxAuthorityRateType[]>) => (this.taxauthorityratetypes = res.body || []));
    });
  }

  updateForm(invoiceItem: IInvoiceItem): void {
    this.editForm.patchValue({
      id: invoiceItem.id,
      sequenceNo: invoiceItem.sequenceNo,
      quantity: invoiceItem.quantity,
      amount: invoiceItem.amount,
      invoice: invoiceItem.invoice,
      invoiceItemType: invoiceItem.invoiceItemType,
      fromInventoryItem: invoiceItem.fromInventoryItem,
      product: invoiceItem.product,
      taxAuthRate: invoiceItem.taxAuthRate,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoiceItem = this.createFromForm();
    if (invoiceItem.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceItemService.update(invoiceItem));
    } else {
      this.subscribeToSaveResponse(this.invoiceItemService.create(invoiceItem));
    }
  }

  private createFromForm(): IInvoiceItem {
    return {
      ...new InvoiceItem(),
      id: this.editForm.get(['id'])!.value,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      invoice: this.editForm.get(['invoice'])!.value,
      invoiceItemType: this.editForm.get(['invoiceItemType'])!.value,
      fromInventoryItem: this.editForm.get(['fromInventoryItem'])!.value,
      product: this.editForm.get(['product'])!.value,
      taxAuthRate: this.editForm.get(['taxAuthRate'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceItem>>): void {
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
