import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactMechType } from 'app/shared/model/contact-mech-type.model';
import { ContactMechTypeService } from './contact-mech-type.service';

@Component({
  templateUrl: './contact-mech-type-delete-dialog.component.html',
})
export class ContactMechTypeDeleteDialogComponent {
  contactMechType?: IContactMechType;

  constructor(
    protected contactMechTypeService: ContactMechTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contactMechTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contactMechTypeListModification');
      this.activeModal.close();
    });
  }
}
