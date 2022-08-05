import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFacilityGroupMember } from 'app/shared/model/facility-group-member.model';
import { FacilityGroupMemberService } from './facility-group-member.service';

@Component({
  templateUrl: './facility-group-member-delete-dialog.component.html',
})
export class FacilityGroupMemberDeleteDialogComponent {
  facilityGroupMember?: IFacilityGroupMember;

  constructor(
    protected facilityGroupMemberService: FacilityGroupMemberService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.facilityGroupMemberService.delete(id).subscribe(() => {
      this.eventManager.broadcast('facilityGroupMemberListModification');
      this.activeModal.close();
    });
  }
}
