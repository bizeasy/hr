import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInventoryItem, InventoryItem } from 'app/shared/model/inventory-item.model';
import { InventoryItemService } from './inventory-item.service';
import { IInventoryItemType } from 'app/shared/model/inventory-item-type.model';
import { InventoryItemTypeService } from 'app/entities/inventory-item-type/inventory-item-type.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';
import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from 'app/entities/facility/facility.service';
import { IUom } from 'app/shared/model/uom.model';
import { UomService } from 'app/entities/uom/uom.service';
import { ILot } from 'app/shared/model/lot.model';
import { LotService } from 'app/entities/lot/lot.service';

type SelectableEntity = IInventoryItemType | IProduct | IParty | IStatus | IFacility | IUom | ILot;

@Component({
  selector: 'sys-inventory-item-update',
  templateUrl: './inventory-item-update.component.html',
})
export class InventoryItemUpdateComponent implements OnInit {
  isSaving = false;
  inventoryitemtypes: IInventoryItemType[] = [];
  products: IProduct[] = [];
  parties: IParty[] = [];
  statuses: IStatus[] = [];
  facilities: IFacility[] = [];
  uoms: IUom[] = [];
  lots: ILot[] = [];

  editForm = this.fb.group({
    id: [],
    receivedDate: [],
    manufacturedDate: [],
    expiryDate: [],
    retestDate: [],
    containerId: [null, [Validators.maxLength(60)]],
    batchNo: [null, [Validators.maxLength(60)]],
    mfgBatchNo: [null, [Validators.maxLength(60)]],
    lotNoStr: [null, [Validators.maxLength(60)]],
    binNumber: [null, [Validators.maxLength(60)]],
    comments: [null, [Validators.maxLength(255)]],
    quantityOnHandTotal: [],
    availableToPromiseTotal: [],
    accountingQuantityTotal: [],
    oldQuantityOnHand: [],
    oldAvailableToPromise: [],
    serialNumber: [null, [Validators.maxLength(255)]],
    softIdentifier: [null, [Validators.maxLength(255)]],
    activationNumber: [null, [Validators.maxLength(255)]],
    activationValidTrue: [],
    unitCost: [],
    inventoryItemType: [],
    product: [],
    supplier: [],
    ownerParty: [],
    status: [],
    facility: [],
    uom: [],
    currencyUom: [],
    lot: [],
  });

  constructor(
    protected inventoryItemService: InventoryItemService,
    protected inventoryItemTypeService: InventoryItemTypeService,
    protected productService: ProductService,
    protected partyService: PartyService,
    protected statusService: StatusService,
    protected facilityService: FacilityService,
    protected uomService: UomService,
    protected lotService: LotService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryItem }) => {
      if (!inventoryItem.id) {
        const today = moment().startOf('day');
        inventoryItem.receivedDate = today;
        inventoryItem.manufacturedDate = today;
        inventoryItem.expiryDate = today;
        inventoryItem.retestDate = today;
        inventoryItem.activationValidTrue = today;
      }

      this.updateForm(inventoryItem);

      this.inventoryItemTypeService
        .query()
        .subscribe((res: HttpResponse<IInventoryItemType[]>) => (this.inventoryitemtypes = res.body || []));

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));

      this.uomService.query().subscribe((res: HttpResponse<IUom[]>) => (this.uoms = res.body || []));

      this.lotService.query().subscribe((res: HttpResponse<ILot[]>) => (this.lots = res.body || []));
    });
  }

  updateForm(inventoryItem: IInventoryItem): void {
    this.editForm.patchValue({
      id: inventoryItem.id,
      receivedDate: inventoryItem.receivedDate ? inventoryItem.receivedDate.format(DATE_TIME_FORMAT) : null,
      manufacturedDate: inventoryItem.manufacturedDate ? inventoryItem.manufacturedDate.format(DATE_TIME_FORMAT) : null,
      expiryDate: inventoryItem.expiryDate ? inventoryItem.expiryDate.format(DATE_TIME_FORMAT) : null,
      retestDate: inventoryItem.retestDate ? inventoryItem.retestDate.format(DATE_TIME_FORMAT) : null,
      containerId: inventoryItem.containerId,
      batchNo: inventoryItem.batchNo,
      mfgBatchNo: inventoryItem.mfgBatchNo,
      lotNoStr: inventoryItem.lotNoStr,
      binNumber: inventoryItem.binNumber,
      comments: inventoryItem.comments,
      quantityOnHandTotal: inventoryItem.quantityOnHandTotal,
      availableToPromiseTotal: inventoryItem.availableToPromiseTotal,
      accountingQuantityTotal: inventoryItem.accountingQuantityTotal,
      oldQuantityOnHand: inventoryItem.oldQuantityOnHand,
      oldAvailableToPromise: inventoryItem.oldAvailableToPromise,
      serialNumber: inventoryItem.serialNumber,
      softIdentifier: inventoryItem.softIdentifier,
      activationNumber: inventoryItem.activationNumber,
      activationValidTrue: inventoryItem.activationValidTrue ? inventoryItem.activationValidTrue.format(DATE_TIME_FORMAT) : null,
      unitCost: inventoryItem.unitCost,
      inventoryItemType: inventoryItem.inventoryItemType,
      product: inventoryItem.product,
      supplier: inventoryItem.supplier,
      ownerParty: inventoryItem.ownerParty,
      status: inventoryItem.status,
      facility: inventoryItem.facility,
      uom: inventoryItem.uom,
      currencyUom: inventoryItem.currencyUom,
      lot: inventoryItem.lot,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventoryItem = this.createFromForm();
    if (inventoryItem.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryItemService.update(inventoryItem));
    } else {
      this.subscribeToSaveResponse(this.inventoryItemService.create(inventoryItem));
    }
  }

  private createFromForm(): IInventoryItem {
    return {
      ...new InventoryItem(),
      id: this.editForm.get(['id'])!.value,
      receivedDate: this.editForm.get(['receivedDate'])!.value
        ? moment(this.editForm.get(['receivedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      manufacturedDate: this.editForm.get(['manufacturedDate'])!.value
        ? moment(this.editForm.get(['manufacturedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      expiryDate: this.editForm.get(['expiryDate'])!.value ? moment(this.editForm.get(['expiryDate'])!.value, DATE_TIME_FORMAT) : undefined,
      retestDate: this.editForm.get(['retestDate'])!.value ? moment(this.editForm.get(['retestDate'])!.value, DATE_TIME_FORMAT) : undefined,
      containerId: this.editForm.get(['containerId'])!.value,
      batchNo: this.editForm.get(['batchNo'])!.value,
      mfgBatchNo: this.editForm.get(['mfgBatchNo'])!.value,
      lotNoStr: this.editForm.get(['lotNoStr'])!.value,
      binNumber: this.editForm.get(['binNumber'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      quantityOnHandTotal: this.editForm.get(['quantityOnHandTotal'])!.value,
      availableToPromiseTotal: this.editForm.get(['availableToPromiseTotal'])!.value,
      accountingQuantityTotal: this.editForm.get(['accountingQuantityTotal'])!.value,
      oldQuantityOnHand: this.editForm.get(['oldQuantityOnHand'])!.value,
      oldAvailableToPromise: this.editForm.get(['oldAvailableToPromise'])!.value,
      serialNumber: this.editForm.get(['serialNumber'])!.value,
      softIdentifier: this.editForm.get(['softIdentifier'])!.value,
      activationNumber: this.editForm.get(['activationNumber'])!.value,
      activationValidTrue: this.editForm.get(['activationValidTrue'])!.value
        ? moment(this.editForm.get(['activationValidTrue'])!.value, DATE_TIME_FORMAT)
        : undefined,
      unitCost: this.editForm.get(['unitCost'])!.value,
      inventoryItemType: this.editForm.get(['inventoryItemType'])!.value,
      product: this.editForm.get(['product'])!.value,
      supplier: this.editForm.get(['supplier'])!.value,
      ownerParty: this.editForm.get(['ownerParty'])!.value,
      status: this.editForm.get(['status'])!.value,
      facility: this.editForm.get(['facility'])!.value,
      uom: this.editForm.get(['uom'])!.value,
      currencyUom: this.editForm.get(['currencyUom'])!.value,
      lot: this.editForm.get(['lot'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryItem>>): void {
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
