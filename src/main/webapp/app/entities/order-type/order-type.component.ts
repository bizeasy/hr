import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrderType } from 'app/shared/model/order-type.model';
import { OrderTypeService } from './order-type.service';
import { OrderTypeDeleteDialogComponent } from './order-type-delete-dialog.component';

@Component({
  selector: 'sys-order-type',
  templateUrl: './order-type.component.html',
})
export class OrderTypeComponent implements OnInit, OnDestroy {
  orderTypes?: IOrderType[];
  eventSubscriber?: Subscription;

  constructor(protected orderTypeService: OrderTypeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.orderTypeService.query().subscribe((res: HttpResponse<IOrderType[]>) => (this.orderTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOrderTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrderType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOrderTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('orderTypeListModification', () => this.loadAll());
  }

  delete(orderType: IOrderType): void {
    const modalRef = this.modalService.open(OrderTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.orderType = orderType;
  }
}
