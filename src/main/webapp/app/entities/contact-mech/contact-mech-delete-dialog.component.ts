import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactMech } from 'app/shared/model/contact-mech.model';
import { ContactMechService } from './contact-mech.service';

@Component({
  templateUrl: './contact-mech-delete-dialog.component.html',
})
export class ContactMechDeleteDialogComponent {
  contactMech?: IContactMech;

  constructor(
    protected contactMechService: ContactMechService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contactMechService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contactMechListModification');
      this.activeModal.close();
    });
  }
}
