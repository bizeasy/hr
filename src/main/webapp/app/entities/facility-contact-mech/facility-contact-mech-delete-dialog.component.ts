import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFacilityContactMech } from 'app/shared/model/facility-contact-mech.model';
import { FacilityContactMechService } from './facility-contact-mech.service';

@Component({
  templateUrl: './facility-contact-mech-delete-dialog.component.html',
})
export class FacilityContactMechDeleteDialogComponent {
  facilityContactMech?: IFacilityContactMech;

  constructor(
    protected facilityContactMechService: FacilityContactMechService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.facilityContactMechService.delete(id).subscribe(() => {
      this.eventManager.broadcast('facilityContactMechListModification');
      this.activeModal.close();
    });
  }
}
