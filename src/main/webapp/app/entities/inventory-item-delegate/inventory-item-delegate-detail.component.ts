import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventoryItemDelegate } from 'app/shared/model/inventory-item-delegate.model';

@Component({
  selector: 'sys-inventory-item-delegate-detail',
  templateUrl: './inventory-item-delegate-detail.component.html',
})
export class InventoryItemDelegateDetailComponent implements OnInit {
  inventoryItemDelegate: IInventoryItemDelegate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryItemDelegate }) => (this.inventoryItemDelegate = inventoryItemDelegate));
  }

  previousState(): void {
    window.history.back();
  }
}
