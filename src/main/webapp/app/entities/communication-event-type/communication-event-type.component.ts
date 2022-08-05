import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommunicationEventType } from 'app/shared/model/communication-event-type.model';
import { CommunicationEventTypeService } from './communication-event-type.service';
import { CommunicationEventTypeDeleteDialogComponent } from './communication-event-type-delete-dialog.component';

@Component({
  selector: 'sys-communication-event-type',
  templateUrl: './communication-event-type.component.html',
})
export class CommunicationEventTypeComponent implements OnInit, OnDestroy {
  communicationEventTypes?: ICommunicationEventType[];
  eventSubscriber?: Subscription;

  constructor(
    protected communicationEventTypeService: CommunicationEventTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.communicationEventTypeService
      .query()
      .subscribe((res: HttpResponse<ICommunicationEventType[]>) => (this.communicationEventTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCommunicationEventTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICommunicationEventType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCommunicationEventTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('communicationEventTypeListModification', () => this.loadAll());
  }

  delete(communicationEventType: ICommunicationEventType): void {
    const modalRef = this.modalService.open(CommunicationEventTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.communicationEventType = communicationEventType;
  }
}
