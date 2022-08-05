import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrderItemType } from 'app/shared/model/order-item-type.model';
import { OrderItemTypeService } from './order-item-type.service';
import { OrderItemTypeDeleteDialogComponent } from './order-item-type-delete-dialog.component';

@Component({
  selector: 'sys-order-item-type',
  templateUrl: './order-item-type.component.html',
})
export class OrderItemTypeComponent implements OnInit, OnDestroy {
  orderItemTypes?: IOrderItemType[];
  eventSubscriber?: Subscription;

  constructor(
    protected orderItemTypeService: OrderItemTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.orderItemTypeService.query().subscribe((res: HttpResponse<IOrderItemType[]>) => (this.orderItemTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOrderItemTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrderItemType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOrderItemTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('orderItemTypeListModification', () => this.loadAll());
  }

  delete(orderItemType: IOrderItemType): void {
    const modalRef = this.modalService.open(OrderItemTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.orderItemType = orderItemType;
  }
}
