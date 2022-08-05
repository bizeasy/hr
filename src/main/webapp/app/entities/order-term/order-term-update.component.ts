import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOrderTerm, OrderTerm } from 'app/shared/model/order-term.model';
import { OrderTermService } from './order-term.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { OrderItemService } from 'app/entities/order-item/order-item.service';
import { ITerm } from 'app/shared/model/term.model';
import { TermService } from 'app/entities/term/term.service';
import { IOrderTermType } from 'app/shared/model/order-term-type.model';
import { OrderTermTypeService } from 'app/entities/order-term-type/order-term-type.service';

type SelectableEntity = IOrder | IOrderItem | ITerm | IOrderTermType;

@Component({
  selector: 'sys-order-term-update',
  templateUrl: './order-term-update.component.html',
})
export class OrderTermUpdateComponent implements OnInit {
  isSaving = false;
  orders: IOrder[] = [];
  orderitems: IOrderItem[] = [];
  terms: ITerm[] = [];
  ordertermtypes: IOrderTermType[] = [];

  editForm = this.fb.group({
    id: [],
    sequenceNo: [],
    name: [],
    detail: [],
    termValue: [],
    termDays: [],
    textValue: [],
    order: [],
    item: [],
    term: [],
    type: [],
  });

  constructor(
    protected orderTermService: OrderTermService,
    protected orderService: OrderService,
    protected orderItemService: OrderItemService,
    protected termService: TermService,
    protected orderTermTypeService: OrderTermTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderTerm }) => {
      this.updateForm(orderTerm);

      this.orderService.query().subscribe((res: HttpResponse<IOrder[]>) => (this.orders = res.body || []));

      this.orderItemService.query().subscribe((res: HttpResponse<IOrderItem[]>) => (this.orderitems = res.body || []));

      this.termService.query().subscribe((res: HttpResponse<ITerm[]>) => (this.terms = res.body || []));

      this.orderTermTypeService.query().subscribe((res: HttpResponse<IOrderTermType[]>) => (this.ordertermtypes = res.body || []));
    });
  }

  updateForm(orderTerm: IOrderTerm): void {
    this.editForm.patchValue({
      id: orderTerm.id,
      sequenceNo: orderTerm.sequenceNo,
      name: orderTerm.name,
      detail: orderTerm.detail,
      termValue: orderTerm.termValue,
      termDays: orderTerm.termDays,
      textValue: orderTerm.textValue,
      order: orderTerm.order,
      item: orderTerm.item,
      term: orderTerm.term,
      type: orderTerm.type,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderTerm = this.createFromForm();
    if (orderTerm.id !== undefined) {
      this.subscribeToSaveResponse(this.orderTermService.update(orderTerm));
    } else {
      this.subscribeToSaveResponse(this.orderTermService.create(orderTerm));
    }
  }

  private createFromForm(): IOrderTerm {
    return {
      ...new OrderTerm(),
      id: this.editForm.get(['id'])!.value,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      name: this.editForm.get(['name'])!.value,
      detail: this.editForm.get(['detail'])!.value,
      termValue: this.editForm.get(['termValue'])!.value,
      termDays: this.editForm.get(['termDays'])!.value,
      textValue: this.editForm.get(['textValue'])!.value,
      order: this.editForm.get(['order'])!.value,
      item: this.editForm.get(['item'])!.value,
      term: this.editForm.get(['term'])!.value,
      type: this.editForm.get(['type'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderTerm>>): void {
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
