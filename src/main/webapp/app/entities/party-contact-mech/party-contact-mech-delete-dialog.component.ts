import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartyContactMech } from 'app/shared/model/party-contact-mech.model';
import { PartyContactMechService } from './party-contact-mech.service';

@Component({
  templateUrl: './party-contact-mech-delete-dialog.component.html',
})
export class PartyContactMechDeleteDialogComponent {
  partyContactMech?: IPartyContactMech;

  constructor(
    protected partyContactMechService: PartyContactMechService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyContactMechService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partyContactMechListModification');
      this.activeModal.close();
    });
  }
}
