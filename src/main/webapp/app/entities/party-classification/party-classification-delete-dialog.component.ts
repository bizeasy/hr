import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartyClassification } from 'app/shared/model/party-classification.model';
import { PartyClassificationService } from './party-classification.service';

@Component({
  templateUrl: './party-classification-delete-dialog.component.html',
})
export class PartyClassificationDeleteDialogComponent {
  partyClassification?: IPartyClassification;

  constructor(
    protected partyClassificationService: PartyClassificationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyClassificationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partyClassificationListModification');
      this.activeModal.close();
    });
  }
}
