import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInventoryTransfer } from 'app/shared/model/inventory-transfer.model';

@Component({
  selector: 'sys-inventory-transfer-detail',
  templateUrl: './inventory-transfer-detail.component.html',
})
export class InventoryTransferDetailComponent implements OnInit {
  inventoryTransfer: IInventoryTransfer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inventoryTransfer }) => (this.inventoryTransfer = inventoryTransfer));
  }

  previousState(): void {
    window.history.back();
  }
}
