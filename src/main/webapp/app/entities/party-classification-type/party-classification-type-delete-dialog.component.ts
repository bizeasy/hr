import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartyClassificationType } from 'app/shared/model/party-classification-type.model';
import { PartyClassificationTypeService } from './party-classification-type.service';

@Component({
  templateUrl: './party-classification-type-delete-dialog.component.html',
})
export class PartyClassificationTypeDeleteDialogComponent {
  partyClassificationType?: IPartyClassificationType;

  constructor(
    protected partyClassificationTypeService: PartyClassificationTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyClassificationTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partyClassificationTypeListModification');
      this.activeModal.close();
    });
  }
}
