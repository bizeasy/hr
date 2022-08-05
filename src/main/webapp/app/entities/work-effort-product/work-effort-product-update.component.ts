import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorkEffortProduct, WorkEffortProduct } from 'app/shared/model/work-effort-product.model';
import { WorkEffortProductService } from './work-effort-product.service';
import { IWorkEffort } from 'app/shared/model/work-effort.model';
import { WorkEffortService } from 'app/entities/work-effort/work-effort.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

type SelectableEntity = IWorkEffort | IProduct;

@Component({
  selector: 'sys-work-effort-product-update',
  templateUrl: './work-effort-product-update.component.html',
})
export class WorkEffortProductUpdateComponent implements OnInit {
  isSaving = false;
  workefforts: IWorkEffort[] = [];
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    sequenceNo: [],
    quantity: [],
    workEffort: [],
    product: [],
  });

  constructor(
    protected workEffortProductService: WorkEffortProductService,
    protected workEffortService: WorkEffortService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffortProduct }) => {
      this.updateForm(workEffortProduct);

      this.workEffortService.query().subscribe((res: HttpResponse<IWorkEffort[]>) => (this.workefforts = res.body || []));

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(workEffortProduct: IWorkEffortProduct): void {
    this.editForm.patchValue({
      id: workEffortProduct.id,
      sequenceNo: workEffortProduct.sequenceNo,
      quantity: workEffortProduct.quantity,
      workEffort: workEffortProduct.workEffort,
      product: workEffortProduct.product,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workEffortProduct = this.createFromForm();
    if (workEffortProduct.id !== undefined) {
      this.subscribeToSaveResponse(this.workEffortProductService.update(workEffortProduct));
    } else {
      this.subscribeToSaveResponse(this.workEffortProductService.create(workEffortProduct));
    }
  }

  private createFromForm(): IWorkEffortProduct {
    return {
      ...new WorkEffortProduct(),
      id: this.editForm.get(['id'])!.value,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      workEffort: this.editForm.get(['workEffort'])!.value,
      product: this.editForm.get(['product'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkEffortProduct>>): void {
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
