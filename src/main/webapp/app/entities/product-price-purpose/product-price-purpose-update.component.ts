import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductPricePurpose, ProductPricePurpose } from 'app/shared/model/product-price-purpose.model';
import { ProductPricePurposeService } from './product-price-purpose.service';

@Component({
  selector: 'sys-product-price-purpose-update',
  templateUrl: './product-price-purpose-update.component.html',
})
export class ProductPricePurposeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(100)]],
  });

  constructor(
    protected productPricePurposeService: ProductPricePurposeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productPricePurpose }) => {
      this.updateForm(productPricePurpose);
    });
  }

  updateForm(productPricePurpose: IProductPricePurpose): void {
    this.editForm.patchValue({
      id: productPricePurpose.id,
      name: productPricePurpose.name,
      description: productPricePurpose.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productPricePurpose = this.createFromForm();
    if (productPricePurpose.id !== undefined) {
      this.subscribeToSaveResponse(this.productPricePurposeService.update(productPricePurpose));
    } else {
      this.subscribeToSaveResponse(this.productPricePurposeService.create(productPricePurpose));
    }
  }

  private createFromForm(): IProductPricePurpose {
    return {
      ...new ProductPricePurpose(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductPricePurpose>>): void {
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
