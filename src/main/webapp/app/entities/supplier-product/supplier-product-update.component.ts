import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISupplierProduct, SupplierProduct } from 'app/shared/model/supplier-product.model';
import { SupplierProductService } from './supplier-product.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IUom } from 'app/shared/model/uom.model';
import { UomService } from 'app/entities/uom/uom.service';

type SelectableEntity = IProduct | IParty | IUom;

@Component({
  selector: 'sys-supplier-product-update',
  templateUrl: './supplier-product-update.component.html',
})
export class SupplierProductUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];
  parties: IParty[] = [];
  uoms: IUom[] = [];

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    minOrderQty: [],
    lastPrice: [],
    shippingPrice: [],
    supplierProductId: [],
    leadTimeDays: [],
    cgst: [],
    igst: [],
    sgst: [],
    totalPrice: [],
    comments: [null, [Validators.maxLength(255)]],
    supplierProductName: [],
    manufacturerName: [],
    product: [],
    supplier: [],
    quantityUom: [],
    currencyUom: [],
    manufacturer: [],
  });

  constructor(
    protected supplierProductService: SupplierProductService,
    protected productService: ProductService,
    protected partyService: PartyService,
    protected uomService: UomService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supplierProduct }) => {
      if (!supplierProduct.id) {
        const today = moment().startOf('day');
        supplierProduct.fromDate = today;
        supplierProduct.thruDate = today;
      }

      this.updateForm(supplierProduct);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.uomService.query().subscribe((res: HttpResponse<IUom[]>) => (this.uoms = res.body || []));
    });
  }

  updateForm(supplierProduct: ISupplierProduct): void {
    this.editForm.patchValue({
      id: supplierProduct.id,
      fromDate: supplierProduct.fromDate ? supplierProduct.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: supplierProduct.thruDate ? supplierProduct.thruDate.format(DATE_TIME_FORMAT) : null,
      minOrderQty: supplierProduct.minOrderQty,
      lastPrice: supplierProduct.lastPrice,
      shippingPrice: supplierProduct.shippingPrice,
      supplierProductId: supplierProduct.supplierProductId,
      leadTimeDays: supplierProduct.leadTimeDays,
      cgst: supplierProduct.cgst,
      igst: supplierProduct.igst,
      sgst: supplierProduct.sgst,
      totalPrice: supplierProduct.totalPrice,
      comments: supplierProduct.comments,
      supplierProductName: supplierProduct.supplierProductName,
      manufacturerName: supplierProduct.manufacturerName,
      product: supplierProduct.product,
      supplier: supplierProduct.supplier,
      quantityUom: supplierProduct.quantityUom,
      currencyUom: supplierProduct.currencyUom,
      manufacturer: supplierProduct.manufacturer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const supplierProduct = this.createFromForm();
    if (supplierProduct.id !== undefined) {
      this.subscribeToSaveResponse(this.supplierProductService.update(supplierProduct));
    } else {
      this.subscribeToSaveResponse(this.supplierProductService.create(supplierProduct));
    }
  }

  private createFromForm(): ISupplierProduct {
    return {
      ...new SupplierProduct(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? moment(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      minOrderQty: this.editForm.get(['minOrderQty'])!.value,
      lastPrice: this.editForm.get(['lastPrice'])!.value,
      shippingPrice: this.editForm.get(['shippingPrice'])!.value,
      supplierProductId: this.editForm.get(['supplierProductId'])!.value,
      leadTimeDays: this.editForm.get(['leadTimeDays'])!.value,
      cgst: this.editForm.get(['cgst'])!.value,
      igst: this.editForm.get(['igst'])!.value,
      sgst: this.editForm.get(['sgst'])!.value,
      totalPrice: this.editForm.get(['totalPrice'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      supplierProductName: this.editForm.get(['supplierProductName'])!.value,
      manufacturerName: this.editForm.get(['manufacturerName'])!.value,
      product: this.editForm.get(['product'])!.value,
      supplier: this.editForm.get(['supplier'])!.value,
      quantityUom: this.editForm.get(['quantityUom'])!.value,
      currencyUom: this.editForm.get(['currencyUom'])!.value,
      manufacturer: this.editForm.get(['manufacturer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplierProduct>>): void {
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
