import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IItemIssuance, ItemIssuance } from 'app/shared/model/item-issuance.model';
import { ItemIssuanceService } from './item-issuance.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { OrderItemService } from 'app/entities/order-item/order-item.service';
import { IInventoryItem } from 'app/shared/model/inventory-item.model';
import { InventoryItemService } from 'app/entities/inventory-item/inventory-item.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IReason } from 'app/shared/model/reason.model';
import { ReasonService } from 'app/entities/reason/reason.service';
import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from 'app/entities/facility/facility.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';

type SelectableEntity = IOrder | IOrderItem | IInventoryItem | IUser | IReason | IFacility | IStatus;

@Component({
  selector: 'sys-item-issuance-update',
  templateUrl: './item-issuance-update.component.html',
})
export class ItemIssuanceUpdateComponent implements OnInit {
  isSaving = false;
  orders: IOrder[] = [];
  orderitems: IOrderItem[] = [];
  inventoryitems: IInventoryItem[] = [];
  users: IUser[] = [];
  reasons: IReason[] = [];
  facilities: IFacility[] = [];
  statuses: IStatus[] = [];

  editForm = this.fb.group({
    id: [],
    message: [null, [Validators.maxLength(255)]],
    issuedDate: [],
    issuedBy: [null, [Validators.maxLength(60)]],
    quantity: [],
    cancelQuantity: [],
    fromDate: [],
    thruDate: [],
    equipmentId: [null, [Validators.maxLength(20)]],
    order: [],
    orderItem: [],
    inventoryItem: [],
    issuedByUserLogin: [],
    varianceReason: [],
    facility: [],
    status: [],
  });

  constructor(
    protected itemIssuanceService: ItemIssuanceService,
    protected orderService: OrderService,
    protected orderItemService: OrderItemService,
    protected inventoryItemService: InventoryItemService,
    protected userService: UserService,
    protected reasonService: ReasonService,
    protected facilityService: FacilityService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemIssuance }) => {
      if (!itemIssuance.id) {
        const today = moment().startOf('day');
        itemIssuance.issuedDate = today;
        itemIssuance.fromDate = today;
        itemIssuance.thruDate = today;
      }

      this.updateForm(itemIssuance);

      this.orderService.query().subscribe((res: HttpResponse<IOrder[]>) => (this.orders = res.body || []));

      this.orderItemService.query().subscribe((res: HttpResponse<IOrderItem[]>) => (this.orderitems = res.body || []));

      this.inventoryItemService.query().subscribe((res: HttpResponse<IInventoryItem[]>) => (this.inventoryitems = res.body || []));

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.reasonService.query().subscribe((res: HttpResponse<IReason[]>) => (this.reasons = res.body || []));

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));
    });
  }

  updateForm(itemIssuance: IItemIssuance): void {
    this.editForm.patchValue({
      id: itemIssuance.id,
      message: itemIssuance.message,
      issuedDate: itemIssuance.issuedDate ? itemIssuance.issuedDate.format(DATE_TIME_FORMAT) : null,
      issuedBy: itemIssuance.issuedBy,
      quantity: itemIssuance.quantity,
      cancelQuantity: itemIssuance.cancelQuantity,
      fromDate: itemIssuance.fromDate ? itemIssuance.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: itemIssuance.thruDate ? itemIssuance.thruDate.format(DATE_TIME_FORMAT) : null,
      equipmentId: itemIssuance.equipmentId,
      order: itemIssuance.order,
      orderItem: itemIssuance.orderItem,
      inventoryItem: itemIssuance.inventoryItem,
      issuedByUserLogin: itemIssuance.issuedByUserLogin,
      varianceReason: itemIssuance.varianceReason,
      facility: itemIssuance.facility,
      status: itemIssuance.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const itemIssuance = this.createFromForm();
    if (itemIssuance.id !== undefined) {
      this.subscribeToSaveResponse(this.itemIssuanceService.update(itemIssuance));
    } else {
      this.subscribeToSaveResponse(this.itemIssuanceService.create(itemIssuance));
    }
  }

  private createFromForm(): IItemIssuance {
    return {
      ...new ItemIssuance(),
      id: this.editForm.get(['id'])!.value,
      message: this.editForm.get(['message'])!.value,
      issuedDate: this.editForm.get(['issuedDate'])!.value ? moment(this.editForm.get(['issuedDate'])!.value, DATE_TIME_FORMAT) : undefined,
      issuedBy: this.editForm.get(['issuedBy'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      cancelQuantity: this.editForm.get(['cancelQuantity'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? moment(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      equipmentId: this.editForm.get(['equipmentId'])!.value,
      order: this.editForm.get(['order'])!.value,
      orderItem: this.editForm.get(['orderItem'])!.value,
      inventoryItem: this.editForm.get(['inventoryItem'])!.value,
      issuedByUserLogin: this.editForm.get(['issuedByUserLogin'])!.value,
      varianceReason: this.editForm.get(['varianceReason'])!.value,
      facility: this.editForm.get(['facility'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemIssuance>>): void {
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
