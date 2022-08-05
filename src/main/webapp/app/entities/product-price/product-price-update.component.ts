import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IProductPrice, ProductPrice } from 'app/shared/model/product-price.model';
import { ProductPriceService } from './product-price.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';
import { IProductPriceType } from 'app/shared/model/product-price-type.model';
import { ProductPriceTypeService } from 'app/entities/product-price-type/product-price-type.service';
import { IProductPricePurpose } from 'app/shared/model/product-price-purpose.model';
import { ProductPricePurposeService } from 'app/entities/product-price-purpose/product-price-purpose.service';
import { IUom } from 'app/shared/model/uom.model';
import { UomService } from 'app/entities/uom/uom.service';

type SelectableEntity = IProduct | IProductPriceType | IProductPricePurpose | IUom;

@Component({
  selector: 'sys-product-price-update',
  templateUrl: './product-price-update.component.html',
})
export class ProductPriceUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];
  productpricetypes: IProductPriceType[] = [];
  productpricepurposes: IProductPricePurpose[] = [];
  uoms: IUom[] = [];

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    price: [],
    cgst: [],
    igst: [],
    sgst: [],
    totalPrice: [],
    product: [],
    productPriceType: [],
    productPricePurpose: [],
    currencyUom: [],
  });

  constructor(
    protected productPriceService: ProductPriceService,
    protected productService: ProductService,
    protected productPriceTypeService: ProductPriceTypeService,
    protected productPricePurposeService: ProductPricePurposeService,
    protected uomService: UomService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productPrice }) => {
      if (!productPrice.id) {
        const today = moment().startOf('day');
        productPrice.fromDate = today;
        productPrice.thruDate = today;
      }

      this.updateForm(productPrice);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.productPriceTypeService.query().subscribe((res: HttpResponse<IProductPriceType[]>) => (this.productpricetypes = res.body || []));

      this.productPricePurposeService
        .query()
        .subscribe((res: HttpResponse<IProductPricePurpose[]>) => (this.productpricepurposes = res.body || []));

      this.uomService.query().subscribe((res: HttpResponse<IUom[]>) => (this.uoms = res.body || []));
    });
  }

  updateForm(productPrice: IProductPrice): void {
    this.editForm.patchValue({
      id: productPrice.id,
      fromDate: productPrice.fromDate ? productPrice.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: productPrice.thruDate ? productPrice.thruDate.format(DATE_TIME_FORMAT) : null,
      price: productPrice.price,
      cgst: productPrice.cgst,
      igst: productPrice.igst,
      sgst: productPrice.sgst,
      totalPrice: productPrice.totalPrice,
      product: productPrice.product,
      productPriceType: productPrice.productPriceType,
      productPricePurpose: productPrice.productPricePurpose,
      currencyUom: productPrice.currencyUom,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productPrice = this.createFromForm();
    if (productPrice.id !== undefined) {
      this.subscribeToSaveResponse(this.productPriceService.update(productPrice));
    } else {
      this.subscribeToSaveResponse(this.productPriceService.create(productPrice));
    }
  }

  private createFromForm(): IProductPrice {
    return {
      ...new ProductPrice(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? moment(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      price: this.editForm.get(['price'])!.value,
      cgst: this.editForm.get(['cgst'])!.value,
      igst: this.editForm.get(['igst'])!.value,
      sgst: this.editForm.get(['sgst'])!.value,
      totalPrice: this.editForm.get(['totalPrice'])!.value,
      product: this.editForm.get(['product'])!.value,
      productPriceType: this.editForm.get(['productPriceType'])!.value,
      productPricePurpose: this.editForm.get(['productPricePurpose'])!.value,
      currencyUom: this.editForm.get(['currencyUom'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductPrice>>): void {
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
