import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInventoryItemType } from 'app/shared/model/inventory-item-type.model';
import { InventoryItemTypeService } from './inventory-item-type.service';
import { InventoryItemTypeDeleteDialogComponent } from './inventory-item-type-delete-dialog.component';

@Component({
  selector: 'sys-inventory-item-type',
  templateUrl: './inventory-item-type.component.html',
})
export class InventoryItemTypeComponent implements OnInit, OnDestroy {
  inventoryItemTypes?: IInventoryItemType[];
  eventSubscriber?: Subscription;

  constructor(
    protected inventoryItemTypeService: InventoryItemTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.inventoryItemTypeService
      .query()
      .subscribe((res: HttpResponse<IInventoryItemType[]>) => (this.inventoryItemTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInventoryItemTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInventoryItemType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInventoryItemTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('inventoryItemTypeListModification', () => this.loadAll());
  }

  delete(inventoryItemType: IInventoryItemType): void {
    const modalRef = this.modalService.open(InventoryItemTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.inventoryItemType = inventoryItemType;
  }
}
