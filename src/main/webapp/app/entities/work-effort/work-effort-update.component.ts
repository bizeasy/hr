import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorkEffort, WorkEffort } from 'app/shared/model/work-effort.model';
import { WorkEffortService } from './work-effort.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';
import { IWorkEffortType } from 'app/shared/model/work-effort-type.model';
import { WorkEffortTypeService } from 'app/entities/work-effort-type/work-effort-type.service';
import { IWorkEffortPurpose } from 'app/shared/model/work-effort-purpose.model';
import { WorkEffortPurposeService } from 'app/entities/work-effort-purpose/work-effort-purpose.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';
import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from 'app/entities/facility/facility.service';
import { IUom } from 'app/shared/model/uom.model';
import { UomService } from 'app/entities/uom/uom.service';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category/product-category.service';
import { IProductStore } from 'app/shared/model/product-store.model';
import { ProductStoreService } from 'app/entities/product-store/product-store.service';

type SelectableEntity = IProduct | IWorkEffortType | IWorkEffortPurpose | IStatus | IFacility | IUom | IProductCategory | IProductStore;

@Component({
  selector: 'sys-work-effort-update',
  templateUrl: './work-effort-update.component.html',
})
export class WorkEffortUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];
  workefforttypes: IWorkEffortType[] = [];
  workeffortpurposes: IWorkEffortPurpose[] = [];
  statuses: IStatus[] = [];
  facilities: IFacility[] = [];
  uoms: IUom[] = [];
  productcategories: IProductCategory[] = [];
  productstores: IProductStore[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(60)]],
    code: [null, [Validators.maxLength(60)]],
    batchSize: [],
    minYieldRange: [],
    maxYieldRange: [],
    percentComplete: [],
    validationType: [null, [Validators.maxLength(25)]],
    shelfLife: [],
    outputQty: [],
    product: [],
    ksm: [],
    type: [],
    purpose: [],
    status: [],
    facility: [],
    shelfListUom: [],
    productCategory: [],
    productStore: [],
  });

  constructor(
    protected workEffortService: WorkEffortService,
    protected productService: ProductService,
    protected workEffortTypeService: WorkEffortTypeService,
    protected workEffortPurposeService: WorkEffortPurposeService,
    protected statusService: StatusService,
    protected facilityService: FacilityService,
    protected uomService: UomService,
    protected productCategoryService: ProductCategoryService,
    protected productStoreService: ProductStoreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workEffort }) => {
      this.updateForm(workEffort);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.workEffortTypeService.query().subscribe((res: HttpResponse<IWorkEffortType[]>) => (this.workefforttypes = res.body || []));

      this.workEffortPurposeService
        .query()
        .subscribe((res: HttpResponse<IWorkEffortPurpose[]>) => (this.workeffortpurposes = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));

      this.uomService.query().subscribe((res: HttpResponse<IUom[]>) => (this.uoms = res.body || []));

      this.productCategoryService.query().subscribe((res: HttpResponse<IProductCategory[]>) => (this.productcategories = res.body || []));

      this.productStoreService.query().subscribe((res: HttpResponse<IProductStore[]>) => (this.productstores = res.body || []));
    });
  }

  updateForm(workEffort: IWorkEffort): void {
    this.editForm.patchValue({
      id: workEffort.id,
      name: workEffort.name,
      description: workEffort.description,
      code: workEffort.code,
      batchSize: workEffort.batchSize,
      minYieldRange: workEffort.minYieldRange,
      maxYieldRange: workEffort.maxYieldRange,
      percentComplete: workEffort.percentComplete,
      validationType: workEffort.validationType,
      shelfLife: workEffort.shelfLife,
      outputQty: workEffort.outputQty,
      product: workEffort.product,
      ksm: workEffort.ksm,
      type: workEffort.type,
      purpose: workEffort.purpose,
      status: workEffort.status,
      facility: workEffort.facility,
      shelfListUom: workEffort.shelfListUom,
      productCategory: workEffort.productCategory,
      productStore: workEffort.productStore,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workEffort = this.createFromForm();
    if (workEffort.id !== undefined) {
      this.subscribeToSaveResponse(this.workEffortService.update(workEffort));
    } else {
      this.subscribeToSaveResponse(this.workEffortService.create(workEffort));
    }
  }

  private createFromForm(): IWorkEffort {
    return {
      ...new WorkEffort(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      code: this.editForm.get(['code'])!.value,
      batchSize: this.editForm.get(['batchSize'])!.value,
      minYieldRange: this.editForm.get(['minYieldRange'])!.value,
      maxYieldRange: this.editForm.get(['maxYieldRange'])!.value,
      percentComplete: this.editForm.get(['percentComplete'])!.value,
      validationType: this.editForm.get(['validationType'])!.value,
      shelfLife: this.editForm.get(['shelfLife'])!.value,
      outputQty: this.editForm.get(['outputQty'])!.value,
      product: this.editForm.get(['product'])!.value,
      ksm: this.editForm.get(['ksm'])!.value,
      type: this.editForm.get(['type'])!.value,
      purpose: this.editForm.get(['purpose'])!.value,
      status: this.editForm.get(['status'])!.value,
      facility: this.editForm.get(['facility'])!.value,
      shelfListUom: this.editForm.get(['shelfListUom'])!.value,
      productCategory: this.editForm.get(['productCategory'])!.value,
      productStore: this.editForm.get(['productStore'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkEffort>>): void {
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
