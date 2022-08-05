import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOrderItemType, OrderItemType } from 'app/shared/model/order-item-type.model';
import { OrderItemTypeService } from './order-item-type.service';

@Component({
  selector: 'sys-order-item-type-update',
  templateUrl: './order-item-type-update.component.html',
})
export class OrderItemTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(100)]],
  });

  constructor(protected orderItemTypeService: OrderItemTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderItemType }) => {
      this.updateForm(orderItemType);
    });
  }

  updateForm(orderItemType: IOrderItemType): void {
    this.editForm.patchValue({
      id: orderItemType.id,
      name: orderItemType.name,
      description: orderItemType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderItemType = this.createFromForm();
    if (orderItemType.id !== undefined) {
      this.subscribeToSaveResponse(this.orderItemTypeService.update(orderItemType));
    } else {
      this.subscribeToSaveResponse(this.orderItemTypeService.create(orderItemType));
    }
  }

  private createFromForm(): IOrderItemType {
    return {
      ...new OrderItemType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderItemType>>): void {
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
