import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommunicationEventType } from 'app/shared/model/communication-event-type.model';
import { CommunicationEventTypeService } from './communication-event-type.service';

@Component({
  templateUrl: './communication-event-type-delete-dialog.component.html',
})
export class CommunicationEventTypeDeleteDialogComponent {
  communicationEventType?: ICommunicationEventType;

  constructor(
    protected communicationEventTypeService: CommunicationEventTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.communicationEventTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('communicationEventTypeListModification');
      this.activeModal.close();
    });
  }
}
