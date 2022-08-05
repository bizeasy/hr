import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductPriceType, ProductPriceType } from 'app/shared/model/product-price-type.model';
import { ProductPriceTypeService } from './product-price-type.service';

@Component({
  selector: 'sys-product-price-type-update',
  templateUrl: './product-price-type-update.component.html',
})
export class ProductPriceTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(100)]],
  });

  constructor(
    protected productPriceTypeService: ProductPriceTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productPriceType }) => {
      this.updateForm(productPriceType);
    });
  }

  updateForm(productPriceType: IProductPriceType): void {
    this.editForm.patchValue({
      id: productPriceType.id,
      name: productPriceType.name,
      description: productPriceType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productPriceType = this.createFromForm();
    if (productPriceType.id !== undefined) {
      this.subscribeToSaveResponse(this.productPriceTypeService.update(productPriceType));
    } else {
      this.subscribeToSaveResponse(this.productPriceTypeService.create(productPriceType));
    }
  }

  private createFromForm(): IProductPriceType {
    return {
      ...new ProductPriceType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductPriceType>>): void {
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
