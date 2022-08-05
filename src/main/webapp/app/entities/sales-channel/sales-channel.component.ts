import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISalesChannel } from 'app/shared/model/sales-channel.model';
import { SalesChannelService } from './sales-channel.service';
import { SalesChannelDeleteDialogComponent } from './sales-channel-delete-dialog.component';

@Component({
  selector: 'sys-sales-channel',
  templateUrl: './sales-channel.component.html',
})
export class SalesChannelComponent implements OnInit, OnDestroy {
  salesChannels?: ISalesChannel[];
  eventSubscriber?: Subscription;

  constructor(
    protected salesChannelService: SalesChannelService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.salesChannelService.query().subscribe((res: HttpResponse<ISalesChannel[]>) => (this.salesChannels = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSalesChannels();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISalesChannel): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSalesChannels(): void {
    this.eventSubscriber = this.eventManager.subscribe('salesChannelListModification', () => this.loadAll());
  }

  delete(salesChannel: ISalesChannel): void {
    const modalRef = this.modalService.open(SalesChannelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.salesChannel = salesChannel;
  }
}
