import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventoryItemType } from 'app/shared/model/inventory-item-type.model';

@Component({
  selector: 'sys-inventory-item-type-detail',
  templateUrl: './inventory-item-type-detail.component.html',
})
export class InventoryItemTypeDetailComponent implements OnInit {
  inventoryItemType: IInventoryItemType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryItemType }) => (this.inventoryItemType = inventoryItemType));
  }

  previousState(): void {
    window.history.back();
  }
}
