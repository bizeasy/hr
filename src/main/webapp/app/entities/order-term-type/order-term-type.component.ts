import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrderTermType } from 'app/shared/model/order-term-type.model';
import { OrderTermTypeService } from './order-term-type.service';
import { OrderTermTypeDeleteDialogComponent } from './order-term-type-delete-dialog.component';

@Component({
  selector: 'sys-order-term-type',
  templateUrl: './order-term-type.component.html',
})
export class OrderTermTypeComponent implements OnInit, OnDestroy {
  orderTermTypes?: IOrderTermType[];
  eventSubscriber?: Subscription;

  constructor(
    protected orderTermTypeService: OrderTermTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.orderTermTypeService.query().subscribe((res: HttpResponse<IOrderTermType[]>) => (this.orderTermTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOrderTermTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrderTermType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOrderTermTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('orderTermTypeListModification', () => this.loadAll());
  }

  delete(orderTermType: IOrderTermType): void {
    const modalRef = this.modalService.open(OrderTermTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.orderTermType = orderTermType;
  }
}
