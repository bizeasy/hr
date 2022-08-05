import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrderContactMech, OrderContactMech } from 'app/shared/model/order-contact-mech.model';
import { OrderContactMechService } from './order-contact-mech.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';
import { IContactMech } from 'app/shared/model/contact-mech.model';
import { ContactMechService } from 'app/entities/contact-mech/contact-mech.service';
import { IContactMechPurpose } from 'app/shared/model/contact-mech-purpose.model';
import { ContactMechPurposeService } from 'app/entities/contact-mech-purpose/contact-mech-purpose.service';

type SelectableEntity = IOrder | IContactMech | IContactMechPurpose;

@Component({
  selector: 'sys-order-contact-mech-update',
  templateUrl: './order-contact-mech-update.component.html',
})
export class OrderContactMechUpdateComponent implements OnInit {
  isSaving = false;
  orders: IOrder[] = [];
  contactmeches: IContactMech[] = [];
  contactmechpurposes: IContactMechPurpose[] = [];

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    order: [],
    contactMech: [],
    contactMechPurpose: [],
  });

  constructor(
    protected orderContactMechService: OrderContactMechService,
    protected orderService: OrderService,
    protected contactMechService: ContactMechService,
    protected contactMechPurposeService: ContactMechPurposeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderContactMech }) => {
      if (!orderContactMech.id) {
        const today = moment().startOf('day');
        orderContactMech.fromDate = today;
        orderContactMech.thruDate = today;
      }

      this.updateForm(orderContactMech);

      this.orderService.query().subscribe((res: HttpResponse<IOrder[]>) => (this.orders = res.body || []));

      this.contactMechService.query().subscribe((res: HttpResponse<IContactMech[]>) => (this.contactmeches = res.body || []));

      this.contactMechPurposeService
        .query()
        .subscribe((res: HttpResponse<IContactMechPurpose[]>) => (this.contactmechpurposes = res.body || []));
    });
  }

  updateForm(orderContactMech: IOrderContactMech): void {
    this.editForm.patchValue({
      id: orderContactMech.id,
      fromDate: orderContactMech.fromDate ? orderContactMech.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: orderContactMech.thruDate ? orderContactMech.thruDate.format(DATE_TIME_FORMAT) : null,
      order: orderContactMech.order,
      contactMech: orderContactMech.contactMech,
      contactMechPurpose: orderContactMech.contactMechPurpose,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderContactMech = this.createFromForm();
    if (orderContactMech.id !== undefined) {
      this.subscribeToSaveResponse(this.orderContactMechService.update(orderContactMech));
    } else {
      this.subscribeToSaveResponse(this.orderContactMechService.create(orderContactMech));
    }
  }

  private createFromForm(): IOrderContactMech {
    return {
      ...new OrderContactMech(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? moment(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      order: this.editForm.get(['order'])!.value,
      contactMech: this.editForm.get(['contactMech'])!.value,
      contactMechPurpose: this.editForm.get(['contactMechPurpose'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderContactMech>>): void {
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
