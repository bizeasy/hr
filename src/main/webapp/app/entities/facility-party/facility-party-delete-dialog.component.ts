import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFacilityParty } from 'app/shared/model/facility-party.model';
import { FacilityPartyService } from './facility-party.service';

@Component({
  templateUrl: './facility-party-delete-dialog.component.html',
})
export class FacilityPartyDeleteDialogComponent {
  facilityParty?: IFacilityParty;

  constructor(
    protected facilityPartyService: FacilityPartyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.facilityPartyService.delete(id).subscribe(() => {
      this.eventManager.broadcast('facilityPartyListModification');
      this.activeModal.close();
    });
  }
}
