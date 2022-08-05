import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IProductCategoryMember, ProductCategoryMember } from 'app/shared/model/product-category-member.model';
import { ProductCategoryMemberService } from './product-category-member.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category/product-category.service';

type SelectableEntity = IProduct | IProductCategory;

@Component({
  selector: 'sys-product-category-member-update',
  templateUrl: './product-category-member-update.component.html',
})
export class ProductCategoryMemberUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];
  productcategories: IProductCategory[] = [];

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    sequenceNo: [],
    product: [],
    productCategory: [],
  });

  constructor(
    protected productCategoryMemberService: ProductCategoryMemberService,
    protected productService: ProductService,
    protected productCategoryService: ProductCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productCategoryMember }) => {
      if (!productCategoryMember.id) {
        const today = moment().startOf('day');
        productCategoryMember.fromDate = today;
        productCategoryMember.thruDate = today;
      }

      this.updateForm(productCategoryMember);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.productCategoryService.query().subscribe((res: HttpResponse<IProductCategory[]>) => (this.productcategories = res.body || []));
    });
  }

  updateForm(productCategoryMember: IProductCategoryMember): void {
    this.editForm.patchValue({
      id: productCategoryMember.id,
      fromDate: productCategoryMember.fromDate ? productCategoryMember.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: productCategoryMember.thruDate ? productCategoryMember.thruDate.format(DATE_TIME_FORMAT) : null,
      sequenceNo: productCategoryMember.sequenceNo,
      product: productCategoryMember.product,
      productCategory: productCategoryMember.productCategory,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productCategoryMember = this.createFromForm();
    if (productCategoryMember.id !== undefined) {
      this.subscribeToSaveResponse(this.productCategoryMemberService.update(productCategoryMember));
    } else {
      this.subscribeToSaveResponse(this.productCategoryMemberService.create(productCategoryMember));
    }
  }

  private createFromForm(): IProductCategoryMember {
    return {
      ...new ProductCategoryMember(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? moment(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      product: this.editForm.get(['product'])!.value,
      productCategory: this.editForm.get(['productCategory'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductCategoryMember>>): void {
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
