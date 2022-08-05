import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IProductStoreProduct, ProductStoreProduct } from 'app/shared/model/product-store-product.model';
import { ProductStoreProductService } from './product-store-product.service';
import { IProductStore } from 'app/shared/model/product-store.model';
import { ProductStoreService } from 'app/entities/product-store/product-store.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

type SelectableEntity = IProductStore | IProduct;

@Component({
  selector: 'sys-product-store-product-update',
  templateUrl: './product-store-product-update.component.html',
})
export class ProductStoreProductUpdateComponent implements OnInit {
  isSaving = false;
  productstores: IProductStore[] = [];
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    sequenceNo: [],
    productStore: [null, Validators.required],
    product: [null, Validators.required],
  });

  constructor(
    protected productStoreProductService: ProductStoreProductService,
    protected productStoreService: ProductStoreService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productStoreProduct }) => {
      if (!productStoreProduct.id) {
        const today = moment().startOf('day');
        productStoreProduct.fromDate = today;
        productStoreProduct.thruDate = today;
      }

      this.updateForm(productStoreProduct);

      this.productStoreService.query().subscribe((res: HttpResponse<IProductStore[]>) => (this.productstores = res.body || []));

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(productStoreProduct: IProductStoreProduct): void {
    this.editForm.patchValue({
      id: productStoreProduct.id,
      fromDate: productStoreProduct.fromDate ? productStoreProduct.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: productStoreProduct.thruDate ? productStoreProduct.thruDate.format(DATE_TIME_FORMAT) : null,
      sequenceNo: productStoreProduct.sequenceNo,
      productStore: productStoreProduct.productStore,
      product: productStoreProduct.product,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productStoreProduct = this.createFromForm();
    if (productStoreProduct.id !== undefined) {
      this.subscribeToSaveResponse(this.productStoreProductService.update(productStoreProduct));
    } else {
      this.subscribeToSaveResponse(this.productStoreProductService.create(productStoreProduct));
    }
  }

  private createFromForm(): IProductStoreProduct {
    return {
      ...new ProductStoreProduct(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? moment(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      productStore: this.editForm.get(['productStore'])!.value,
      product: this.editForm.get(['product'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductStoreProduct>>): void {
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
