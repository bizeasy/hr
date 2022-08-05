import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOrderTermType, OrderTermType } from 'app/shared/model/order-term-type.model';
import { OrderTermTypeService } from './order-term-type.service';

@Component({
  selector: 'sys-order-term-type-update',
  templateUrl: './order-term-type-update.component.html',
})
export class OrderTermTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(60)]],
  });

  constructor(protected orderTermTypeService: OrderTermTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderTermType }) => {
      this.updateForm(orderTermType);
    });
  }

  updateForm(orderTermType: IOrderTermType): void {
    this.editForm.patchValue({
      id: orderTermType.id,
      name: orderTermType.name,
      description: orderTermType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderTermType = this.createFromForm();
    if (orderTermType.id !== undefined) {
      this.subscribeToSaveResponse(this.orderTermTypeService.update(orderTermType));
    } else {
      this.subscribeToSaveResponse(this.orderTermTypeService.create(orderTermType));
    }
  }

  private createFromForm(): IOrderTermType {
    return {
      ...new OrderTermType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderTermType>>): void {
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
