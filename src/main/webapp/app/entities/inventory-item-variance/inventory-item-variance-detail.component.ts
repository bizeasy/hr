import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventoryItemVariance } from 'app/shared/model/inventory-item-variance.model';

@Component({
  selector: 'sys-inventory-item-variance-detail',
  templateUrl: './inventory-item-variance-detail.component.html',
})
export class InventoryItemVarianceDetailComponent implements OnInit {
  inventoryItemVariance: IInventoryItemVariance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryItemVariance }) => (this.inventoryItemVariance = inventoryItemVariance));
  }

  previousState(): void {
    window.history.back();
  }
}
