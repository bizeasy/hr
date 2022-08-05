import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartyType } from 'app/shared/model/party-type.model';
import { PartyTypeService } from './party-type.service';

@Component({
  templateUrl: './party-type-delete-dialog.component.html',
})
export class PartyTypeDeleteDialogComponent {
  partyType?: IPartyType;

  constructor(protected partyTypeService: PartyTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partyTypeListModification');
      this.activeModal.close();
    });
  }
}
