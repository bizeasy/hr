import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInventoryTransfer, InventoryTransfer } from 'app/shared/model/inventory-transfer.model';
import { InventoryTransferService } from './inventory-transfer.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';
import { IInventoryItem } from 'app/shared/model/inventory-item.model';
import { InventoryItemService } from 'app/entities/inventory-item/inventory-item.service';
import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from 'app/entities/facility/facility.service';
import { IItemIssuance } from 'app/shared/model/item-issuance.model';
import { ItemIssuanceService } from 'app/entities/item-issuance/item-issuance.service';

type SelectableEntity = IStatus | IInventoryItem | IFacility | IItemIssuance;

@Component({
  selector: 'sys-inventory-transfer-update',
  templateUrl: './inventory-transfer-update.component.html',
})
export class InventoryTransferUpdateComponent implements OnInit {
  isSaving = false;
  statuses: IStatus[] = [];
  inventoryitems: IInventoryItem[] = [];
  facilities: IFacility[] = [];
  itemissuances: IItemIssuance[] = [];

  editForm = this.fb.group({
    id: [],
    sentDate: [],
    receivedDate: [],
    comments: [null, [Validators.maxLength(255)]],
    status: [],
    inventoryItem: [],
    facility: [],
    facilityTo: [],
    issuance: [],
  });

  constructor(
    protected inventoryTransferService: InventoryTransferService,
    protected statusService: StatusService,
    protected inventoryItemService: InventoryItemService,
    protected facilityService: FacilityService,
    protected itemIssuanceService: ItemIssuanceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryTransfer }) => {
      if (!inventoryTransfer.id) {
        const today = moment().startOf('day');
        inventoryTransfer.sentDate = today;
        inventoryTransfer.receivedDate = today;
      }

      this.updateForm(inventoryTransfer);

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));

      this.inventoryItemService.query().subscribe((res: HttpResponse<IInventoryItem[]>) => (this.inventoryitems = res.body || []));

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));

      this.itemIssuanceService.query().subscribe((res: HttpResponse<IItemIssuance[]>) => (this.itemissuances = res.body || []));
    });
  }

  updateForm(inventoryTransfer: IInventoryTransfer): void {
    this.editForm.patchValue({
      id: inventoryTransfer.id,
      sentDate: inventoryTransfer.sentDate ? inventoryTransfer.sentDate.format(DATE_TIME_FORMAT) : null,
      receivedDate: inventoryTransfer.receivedDate ? inventoryTransfer.receivedDate.format(DATE_TIME_FORMAT) : null,
      comments: inventoryTransfer.comments,
      status: inventoryTransfer.status,
      inventoryItem: inventoryTransfer.inventoryItem,
      facility: inventoryTransfer.facility,
      facilityTo: inventoryTransfer.facilityTo,
      issuance: inventoryTransfer.issuance,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventoryTransfer = this.createFromForm();
    if (inventoryTransfer.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryTransferService.update(inventoryTransfer));
    } else {
      this.subscribeToSaveResponse(this.inventoryTransferService.create(inventoryTransfer));
    }
  }

  private createFromForm(): IInventoryTransfer {
    return {
      ...new InventoryTransfer(),
      id: this.editForm.get(['id'])!.value,
      sentDate: this.editForm.get(['sentDate'])!.value ? moment(this.editForm.get(['sentDate'])!.value, DATE_TIME_FORMAT) : undefined,
      receivedDate: this.editForm.get(['receivedDate'])!.value
        ? moment(this.editForm.get(['receivedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      comments: this.editForm.get(['comments'])!.value,
      status: this.editForm.get(['status'])!.value,
      inventoryItem: this.editForm.get(['inventoryItem'])!.value,
      facility: this.editForm.get(['facility'])!.value,
      facilityTo: this.editForm.get(['facilityTo'])!.value,
      issuance: this.editForm.get(['issuance'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryTransfer>>): void {
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
