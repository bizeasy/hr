import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { IOrderType } from 'app/shared/model/order-type.model';
import { OrderTypeService } from 'app/entities/order-type/order-type.service';
import { ISalesChannel } from 'app/shared/model/sales-channel.model';
import { SalesChannelService } from 'app/entities/sales-channel/sales-channel.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';

type SelectableEntity = IOrderType | ISalesChannel | IParty | IStatus;

@Component({
  selector: 'sys-order-update',
  templateUrl: './order-update.component.html',
})
export class OrderUpdateComponent implements OnInit {
  isSaving = false;
  ordertypes: IOrderType[] = [];
  saleschannels: ISalesChannel[] = [];
  parties: IParty[] = [];
  statuses: IStatus[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    externalId: [null, [Validators.maxLength(25)]],
    orderDate: [],
    priority: [null, [Validators.max(10)]],
    entryDate: [],
    isRushOrder: [],
    needsInventoryIssuance: [],
    remainingSubTotal: [],
    grandTotal: [],
    hasRateContract: [],
    gotQuoteFromVendors: [],
    vendorReason: [],
    expectedDeliveryDate: [],
    insuranceResp: [],
    transportResp: [],
    unloadingResp: [],
    orderType: [],
    salesChannel: [],
    party: [],
    status: [],
  });

  constructor(
    protected orderService: OrderService,
    protected orderTypeService: OrderTypeService,
    protected salesChannelService: SalesChannelService,
    protected partyService: PartyService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ order }) => {
      if (!order.id) {
        const today = moment().startOf('day');
        order.orderDate = today;
        order.entryDate = today;
        order.expectedDeliveryDate = today;
      }

      this.updateForm(order);

      this.orderTypeService.query().subscribe((res: HttpResponse<IOrderType[]>) => (this.ordertypes = res.body || []));

      this.salesChannelService.query().subscribe((res: HttpResponse<ISalesChannel[]>) => (this.saleschannels = res.body || []));

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));
    });
  }

  updateForm(order: IOrder): void {
    this.editForm.patchValue({
      id: order.id,
      name: order.name,
      externalId: order.externalId,
      orderDate: order.orderDate ? order.orderDate.format(DATE_TIME_FORMAT) : null,
      priority: order.priority,
      entryDate: order.entryDate ? order.entryDate.format(DATE_TIME_FORMAT) : null,
      isRushOrder: order.isRushOrder,
      needsInventoryIssuance: order.needsInventoryIssuance,
      remainingSubTotal: order.remainingSubTotal,
      grandTotal: order.grandTotal,
      hasRateContract: order.hasRateContract,
      gotQuoteFromVendors: order.gotQuoteFromVendors,
      vendorReason: order.vendorReason,
      expectedDeliveryDate: order.expectedDeliveryDate ? order.expectedDeliveryDate.format(DATE_TIME_FORMAT) : null,
      insuranceResp: order.insuranceResp,
      transportResp: order.transportResp,
      unloadingResp: order.unloadingResp,
      orderType: order.orderType,
      salesChannel: order.salesChannel,
      party: order.party,
      status: order.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  private createFromForm(): IOrder {
    return {
      ...new Order(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      externalId: this.editForm.get(['externalId'])!.value,
      orderDate: this.editForm.get(['orderDate'])!.value ? moment(this.editForm.get(['orderDate'])!.value, DATE_TIME_FORMAT) : undefined,
      priority: this.editForm.get(['priority'])!.value,
      entryDate: this.editForm.get(['entryDate'])!.value ? moment(this.editForm.get(['entryDate'])!.value, DATE_TIME_FORMAT) : undefined,
      isRushOrder: this.editForm.get(['isRushOrder'])!.value,
      needsInventoryIssuance: this.editForm.get(['needsInventoryIssuance'])!.value,
      remainingSubTotal: this.editForm.get(['remainingSubTotal'])!.value,
      grandTotal: this.editForm.get(['grandTotal'])!.value,
      hasRateContract: this.editForm.get(['hasRateContract'])!.value,
      gotQuoteFromVendors: this.editForm.get(['gotQuoteFromVendors'])!.value,
      vendorReason: this.editForm.get(['vendorReason'])!.value,
      expectedDeliveryDate: this.editForm.get(['expectedDeliveryDate'])!.value
        ? moment(this.editForm.get(['expectedDeliveryDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      insuranceResp: this.editForm.get(['insuranceResp'])!.value,
      transportResp: this.editForm.get(['transportResp'])!.value,
      unloadingResp: this.editForm.get(['unloadingResp'])!.value,
      orderType: this.editForm.get(['orderType'])!.value,
      salesChannel: this.editForm.get(['salesChannel'])!.value,
      party: this.editForm.get(['party'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>): void {
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
