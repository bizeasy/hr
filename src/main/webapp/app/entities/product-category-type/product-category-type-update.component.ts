import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductCategoryType, ProductCategoryType } from 'app/shared/model/product-category-type.model';
import { ProductCategoryTypeService } from './product-category-type.service';

@Component({
  selector: 'sys-product-category-type-update',
  templateUrl: './product-category-type-update.component.html',
})
export class ProductCategoryTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(100)]],
  });

  constructor(
    protected productCategoryTypeService: ProductCategoryTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productCategoryType }) => {
      this.updateForm(productCategoryType);
    });
  }

  updateForm(productCategoryType: IProductCategoryType): void {
    this.editForm.patchValue({
      id: productCategoryType.id,
      name: productCategoryType.name,
      description: productCategoryType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productCategoryType = this.createFromForm();
    if (productCategoryType.id !== undefined) {
      this.subscribeToSaveResponse(this.productCategoryTypeService.update(productCategoryType));
    } else {
      this.subscribeToSaveResponse(this.productCategoryTypeService.create(productCategoryType));
    }
  }

  private createFromForm(): IProductCategoryType {
    return {
      ...new ProductCategoryType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductCategoryType>>): void {
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
}
