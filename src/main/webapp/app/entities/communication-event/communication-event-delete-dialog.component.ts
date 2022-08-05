import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommunicationEvent } from 'app/shared/model/communication-event.model';
import { CommunicationEventService } from './communication-event.service';

@Component({
  templateUrl: './communication-event-delete-dialog.component.html',
})
export class CommunicationEventDeleteDialogComponent {
  communicationEvent?: ICommunicationEvent;

  constructor(
    protected communicationEventService: CommunicationEventService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.communicationEventService.delete(id).subscribe(() => {
      this.eventManager.broadcast('communicationEventListModification');
      this.activeModal.close();
    });
  }
}
