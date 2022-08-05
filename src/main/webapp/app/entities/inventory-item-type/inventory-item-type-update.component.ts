import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInventoryItemType, InventoryItemType } from 'app/shared/model/inventory-item-type.model';
import { InventoryItemTypeService } from './inventory-item-type.service';

@Component({
  selector: 'sys-inventory-item-type-update',
  templateUrl: './inventory-item-type-update.component.html',
})
export class InventoryItemTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(100)]],
  });

  constructor(
    protected inventoryItemTypeService: InventoryItemTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryItemType }) => {
      this.updateForm(inventoryItemType);
    });
  }

  updateForm(inventoryItemType: IInventoryItemType): void {
    this.editForm.patchValue({
      id: inventoryItemType.id,
      name: inventoryItemType.name,
      description: inventoryItemType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inventoryItemType = this.createFromForm();
    if (inventoryItemType.id !== undefined) {
      this.subscribeToSaveResponse(this.inventoryItemTypeService.update(inventoryItemType));
    } else {
      this.subscribeToSaveResponse(this.inventoryItemTypeService.create(inventoryItemType));
    }
  }

  private createFromForm(): IInventoryItemType {
    return {
      ...new InventoryItemType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInventoryItemType>>): void {
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
