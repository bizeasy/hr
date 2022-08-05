import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmplPositionFulfillment } from 'app/shared/model/empl-position-fulfillment.model';
import { EmplPositionFulfillmentService } from './empl-position-fulfillment.service';
import { EmplPositionFulfillmentDeleteDialogComponent } from './empl-position-fulfillment-delete-dialog.component';

@Component({
  selector: 'sys-empl-position-fulfillment',
  templateUrl: './empl-position-fulfillment.component.html',
})
export class EmplPositionFulfillmentComponent implements OnInit, OnDestroy {
  emplPositionFulfillments?: IEmplPositionFulfillment[];
  eventSubscriber?: Subscription;

  constructor(
    protected emplPositionFulfillmentService: EmplPositionFulfillmentService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.emplPositionFulfillmentService
      .query()
      .subscribe((res: HttpResponse<IEmplPositionFulfillment[]>) => (this.emplPositionFulfillments = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEmplPositionFulfillments();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmplPositionFulfillment): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmplPositionFulfillments(): void {
    this.eventSubscriber = this.eventManager.subscribe('emplPositionFulfillmentListModification', () => this.loadAll());
  }

  delete(emplPositionFulfillment: IEmplPositionFulfillment): void {
    const modalRef = this.modalService.open(EmplPositionFulfillmentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.emplPositionFulfillment = emplPositionFulfillment;
  }
}
