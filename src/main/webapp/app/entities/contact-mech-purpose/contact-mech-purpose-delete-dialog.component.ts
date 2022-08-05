import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactMechPurpose } from 'app/shared/model/contact-mech-purpose.model';
import { ContactMechPurposeService } from './contact-mech-purpose.service';

@Component({
  templateUrl: './contact-mech-purpose-delete-dialog.component.html',
})
export class ContactMechPurposeDeleteDialogComponent {
  contactMechPurpose?: IContactMechPurpose;

  constructor(
    protected contactMechPurposeService: ContactMechPurposeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contactMechPurposeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contactMechPurposeListModification');
      this.activeModal.close();
    });
  }
}
