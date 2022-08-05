import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommunicationEvent } from 'app/shared/model/communication-event.model';
import { CommunicationEventService } from './communication-event.service';
import { CommunicationEventDeleteDialogComponent } from './communication-event-delete-dialog.component';

@Component({
  selector: 'sys-communication-event',
  templateUrl: './communication-event.component.html',
})
export class CommunicationEventComponent implements OnInit, OnDestroy {
  communicationEvents?: ICommunicationEvent[];
  eventSubscriber?: Subscription;

  constructor(
    protected communicationEventService: CommunicationEventService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.communicationEventService
      .query()
      .subscribe((res: HttpResponse<ICommunicationEvent[]>) => (this.communicationEvents = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCommunicationEvents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICommunicationEvent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInCommunicationEvents(): void {
    this.eventSubscriber = this.eventManager.subscribe('communicationEventListModification', () => this.loadAll());
  }

  delete(communicationEvent: ICommunicationEvent): void {
    const modalRef = this.modalService.open(CommunicationEventDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.communicationEvent = communicationEvent;
  }
}
