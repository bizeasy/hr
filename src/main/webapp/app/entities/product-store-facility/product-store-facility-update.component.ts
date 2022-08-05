import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IProductStoreFacility, ProductStoreFacility } from 'app/shared/model/product-store-facility.model';
import { ProductStoreFacilityService } from './product-store-facility.service';
import { IProductStore } from 'app/shared/model/product-store.model';
import { ProductStoreService } from 'app/entities/product-store/product-store.service';
import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from 'app/entities/facility/facility.service';

type SelectableEntity = IProductStore | IFacility;

@Component({
  selector: 'sys-product-store-facility-update',
  templateUrl: './product-store-facility-update.component.html',
})
export class ProductStoreFacilityUpdateComponent implements OnInit {
  isSaving = false;
  productstores: IProductStore[] = [];
  facilities: IFacility[] = [];

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    sequenceNo: [],
    productStore: [null, Validators.required],
    facility: [null, Validators.required],
  });

  constructor(
    protected productStoreFacilityService: ProductStoreFacilityService,
    protected productStoreService: ProductStoreService,
    protected facilityService: FacilityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productStoreFacility }) => {
      if (!productStoreFacility.id) {
        const today = moment().startOf('day');
        productStoreFacility.fromDate = today;
        productStoreFacility.thruDate = today;
      }

      this.updateForm(productStoreFacility);

      this.productStoreService.query().subscribe((res: HttpResponse<IProductStore[]>) => (this.productstores = res.body || []));

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));
    });
  }

  updateForm(productStoreFacility: IProductStoreFacility): void {
    this.editForm.patchValue({
      id: productStoreFacility.id,
      fromDate: productStoreFacility.fromDate ? productStoreFacility.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: productStoreFacility.thruDate ? productStoreFacility.thruDate.format(DATE_TIME_FORMAT) : null,
      sequenceNo: productStoreFacility.sequenceNo,
      productStore: productStoreFacility.productStore,
      facility: productStoreFacility.facility,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productStoreFacility = this.createFromForm();
    if (productStoreFacility.id !== undefined) {
      this.subscribeToSaveResponse(this.productStoreFacilityService.update(productStoreFacility));
    } else {
      this.subscribeToSaveResponse(this.productStoreFacilityService.create(productStoreFacility));
    }
  }

  private createFromForm(): IProductStoreFacility {
    return {
      ...new ProductStoreFacility(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? moment(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      productStore: this.editForm.get(['productStore'])!.value,
      facility: this.editForm.get(['facility'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductStoreFacility>>): void {
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
