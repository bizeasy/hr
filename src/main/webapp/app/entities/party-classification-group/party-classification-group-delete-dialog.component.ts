import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartyClassificationGroup } from 'app/shared/model/party-classification-group.model';
import { PartyClassificationGroupService } from './party-classification-group.service';

@Component({
  templateUrl: './party-classification-group-delete-dialog.component.html',
})
export class PartyClassificationGroupDeleteDialogComponent {
  partyClassificationGroup?: IPartyClassificationGroup;

  constructor(
    protected partyClassificationGroupService: PartyClassificationGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyClassificationGroupService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partyClassificationGroupListModification');
      this.activeModal.close();
    });
  }
}
