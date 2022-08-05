import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartyRole } from 'app/shared/model/party-role.model';
import { PartyRoleService } from './party-role.service';

@Component({
  templateUrl: './party-role-delete-dialog.component.html',
})
export class PartyRoleDeleteDialogComponent {
  partyRole?: IPartyRole;

  constructor(protected partyRoleService: PartyRoleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyRoleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partyRoleListModification');
      this.activeModal.close();
    });
  }
}
