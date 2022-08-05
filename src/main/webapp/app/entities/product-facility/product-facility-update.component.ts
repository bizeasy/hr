import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductFacility, ProductFacility } from 'app/shared/model/product-facility.model';
import { ProductFacilityService } from './product-facility.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';
import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from 'app/entities/facility/facility.service';

type SelectableEntity = IProduct | IFacility;

@Component({
  selector: 'sys-product-facility-update',
  templateUrl: './product-facility-update.component.html',
})
export class ProductFacilityUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];
  facilities: IFacility[] = [];

  editForm = this.fb.group({
    id: [],
    minimumStock: [],
    reorderQty: [],
    daysToShip: [],
    lastInventoryCount: [],
    product: [],
    facility: [],
  });

  constructor(
    protected productFacilityService: ProductFacilityService,
    protected productService: ProductService,
    protected facilityService: FacilityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productFacility }) => {
      this.updateForm(productFacility);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));
    });
  }

  updateForm(productFacility: IProductFacility): void {
    this.editForm.patchValue({
      id: productFacility.id,
      minimumStock: productFacility.minimumStock,
      reorderQty: productFacility.reorderQty,
      daysToShip: productFacility.daysToShip,
      lastInventoryCount: productFacility.lastInventoryCount,
      product: productFacility.product,
      facility: productFacility.facility,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productFacility = this.createFromForm();
    if (productFacility.id !== undefined) {
      this.subscribeToSaveResponse(this.productFacilityService.update(productFacility));
    } else {
      this.subscribeToSaveResponse(this.productFacilityService.create(productFacility));
    }
  }

  private createFromForm(): IProductFacility {
    return {
      ...new ProductFacility(),
      id: this.editForm.get(['id'])!.value,
      minimumStock: this.editForm.get(['minimumStock'])!.value,
      reorderQty: this.editForm.get(['reorderQty'])!.value,
      daysToShip: this.editForm.get(['daysToShip'])!.value,
      lastInventoryCount: this.editForm.get(['lastInventoryCount'])!.value,
      product: this.editForm.get(['product'])!.value,
      facility: this.editForm.get(['facility'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductFacility>>): void {
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
