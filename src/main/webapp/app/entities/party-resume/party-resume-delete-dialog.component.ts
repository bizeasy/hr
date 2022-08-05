import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartyResume } from 'app/shared/model/party-resume.model';
import { PartyResumeService } from './party-resume.service';

@Component({
  templateUrl: './party-resume-delete-dialog.component.html',
})
export class PartyResumeDeleteDialogComponent {
  partyResume?: IPartyResume;

  constructor(
    protected partyResumeService: PartyResumeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partyResumeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partyResumeListModification');
      this.activeModal.close();
    });
  }
}
