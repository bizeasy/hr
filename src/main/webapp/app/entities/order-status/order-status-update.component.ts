import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrderStatus, OrderStatus } from 'app/shared/model/order-status.model';
import { OrderStatusService } from './order-status.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';
import { IReason } from 'app/shared/model/reason.model';
import { ReasonService } from 'app/entities/reason/reason.service';

type SelectableEntity = IStatus | IOrder | IReason;

@Component({
  selector: 'sys-order-status-update',
  templateUrl: './order-status-update.component.html',
})
export class OrderStatusUpdateComponent implements OnInit {
  isSaving = false;
  statuses: IStatus[] = [];
  orders: IOrder[] = [];
  reasons: IReason[] = [];

  editForm = this.fb.group({
    id: [],
    statusDateTime: [],
    sequenceNo: [],
    status: [],
    order: [],
    reason: [],
  });

  constructor(
    protected orderStatusService: OrderStatusService,
    protected statusService: StatusService,
    protected orderService: OrderService,
    protected reasonService: ReasonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderStatus }) => {
      if (!orderStatus.id) {
        const today = moment().startOf('day');
        orderStatus.statusDateTime = today;
      }

      this.updateForm(orderStatus);

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));

      this.orderService.query().subscribe((res: HttpResponse<IOrder[]>) => (this.orders = res.body || []));

      this.reasonService.query().subscribe((res: HttpResponse<IReason[]>) => (this.reasons = res.body || []));
    });
  }

  updateForm(orderStatus: IOrderStatus): void {
    this.editForm.patchValue({
      id: orderStatus.id,
      statusDateTime: orderStatus.statusDateTime ? orderStatus.statusDateTime.format(DATE_TIME_FORMAT) : null,
      sequenceNo: orderStatus.sequenceNo,
      status: orderStatus.status,
      order: orderStatus.order,
      reason: orderStatus.reason,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderStatus = this.createFromForm();
    if (orderStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.orderStatusService.update(orderStatus));
    } else {
      this.subscribeToSaveResponse(this.orderStatusService.create(orderStatus));
    }
  }

  private createFromForm(): IOrderStatus {
    return {
      ...new OrderStatus(),
      id: this.editForm.get(['id'])!.value,
      statusDateTime: this.editForm.get(['statusDateTime'])!.value
        ? moment(this.editForm.get(['statusDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      status: this.editForm.get(['status'])!.value,
      order: this.editForm.get(['order'])!.value,
      reason: this.editForm.get(['reason'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderStatus>>): void {
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
