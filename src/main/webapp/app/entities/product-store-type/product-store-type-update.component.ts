import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductStoreType, ProductStoreType } from 'app/shared/model/product-store-type.model';
import { ProductStoreTypeService } from './product-store-type.service';

@Component({
  selector: 'sys-product-store-type-update',
  templateUrl: './product-store-type-update.component.html',
})
export class ProductStoreTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(60)]],
  });

  constructor(
    protected productStoreTypeService: ProductStoreTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productStoreType }) => {
      this.updateForm(productStoreType);
    });
  }

  updateForm(productStoreType: IProductStoreType): void {
    this.editForm.patchValue({
      id: productStoreType.id,
      name: productStoreType.name,
      description: productStoreType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productStoreType = this.createFromForm();
    if (productStoreType.id !== undefined) {
      this.subscribeToSaveResponse(this.productStoreTypeService.update(productStoreType));
    } else {
      this.subscribeToSaveResponse(this.productStoreTypeService.create(productStoreType));
    }
  }

  private createFromForm(): IProductStoreType {
    return {
      ...new ProductStoreType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductStoreType>>): void {
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
