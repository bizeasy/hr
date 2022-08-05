import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISkillType } from 'app/shared/model/skill-type.model';
import { SkillTypeService } from './skill-type.service';

@Component({
  templateUrl: './skill-type-delete-dialog.component.html',
})
export class SkillTypeDeleteDialogComponent {
  skillType?: ISkillType;

  constructor(protected skillTypeService: SkillTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.skillTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('skillTypeListModification');
      this.activeModal.close();
    });
  }
}
