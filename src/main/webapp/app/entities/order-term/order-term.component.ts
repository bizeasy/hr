import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrderTerm } from 'app/shared/model/order-term.model';
import { OrderTermService } from './order-term.service';
import { OrderTermDeleteDialogComponent } from './order-term-delete-dialog.component';

@Component({
  selector: 'sys-order-term',
  templateUrl: './order-term.component.html',
})
export class OrderTermComponent implements OnInit, OnDestroy {
  orderTerms?: IOrderTerm[];
  eventSubscriber?: Subscription;

  constructor(protected orderTermService: OrderTermService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.orderTermService.query().subscribe((res: HttpResponse<IOrderTerm[]>) => (this.orderTerms = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOrderTerms();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOrderTerm): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOrderTerms(): void {
    this.eventSubscriber = this.eventManager.subscribe('orderTermListModification', () => this.loadAll());
  }

  delete(orderTerm: IOrderTerm): void {
    const modalRef = this.modalService.open(OrderTermDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.orderTerm = orderTerm;
  }
}
