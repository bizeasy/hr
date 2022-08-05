import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInventoryItemVariance, InventoryItemVariance } from 'app/shared/model/inventory-item-variance.model';
import { InventoryItemVarianceService } from './inventory-item-variance.service';
import { IInventoryItem } from 'app/shared/model/inventory-item.model';
import { InventoryItemService } from 'app/entities/inventory-item/inventory-item.service';
import { IReason } from 'app/shared/model/reason.model';
import { ReasonService } from 'app/entities/reason/reason.service';

type SelectableEntity = IInventoryItem | IReason;

@Component({
  selector: 'sys-inventory-item-variance-update',
  templateUrl: './inventory-item-variance-update.component.html',
})
export class InventoryItemVarianceUpdateComponent implements OnInit {
  isSaving = false;
  inventoryitems: IInventoryItem[] = [];
  reasons: IReason[] = [];

  editForm = this.fb.group({
    id: [],
    varianceReason: [null, [Validators.maxLength(255)]],
    atpVar: [],
    qohVar: [],
    comments: [null, [Validators.maxLength(255)]],
    inventoryItem: [],
    reason: [],
  });

  constructor(
    protected inventoryItemVarianceService: InventoryItemVarianceService,
    protected inventoryItemService: InventoryItemService,
    protected reasonService: ReasonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryItemVariance }) => {
      this.updateForm(inventoryItemVariance);

      this.inventoryItemService.query().subscribe((res: HttpResponse<IInventoryItem[]>) => (this.inventoryitems = res.body || []));

      this.reasonService.query().subscribe((res: HttpResponse<IReason[]>) => (this.reasons = res.body || []));
    });
  }

  updateForm(inventoryItemVariance: IInventoryItemVariance): void {
    this.editForm.patchValue({
      id: inventoryItemVariance.id,
      varianceReason: inventoryItemVariance.varianceReason,
      atpVar: inventoryItemVariance.atpVar,
      qohVar: inventoryItemVariance.qohVar,
      comments: inventoryItemVariance.comments,
      inventoryItem: inventoryItemVariance.inventoryItem,
      reason: inventoryItemVariance.reason,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventoryItemVariance = this.createFromForm();
    if (inventoryItemVariance.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryItemVarianceService.update(inventoryItemVariance));
    } else {
      this.subscribeToSaveResponse(this.inventoryItemVarianceService.create(inventoryItemVariance));
    }
  }

  private createFromForm(): IInventoryItemVariance {
    return {
      ...new InventoryItemVariance(),
      id: this.editForm.get(['id'])!.value,
      varianceReason: this.editForm.get(['varianceReason'])!.value,
      atpVar: this.editForm.get(['atpVar'])!.value,
      qohVar: this.editForm.get(['qohVar'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      inventoryItem: this.editForm.get(['inventoryItem'])!.value,
      reason: this.editForm.get(['reason'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryItemVariance>>): void {
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
