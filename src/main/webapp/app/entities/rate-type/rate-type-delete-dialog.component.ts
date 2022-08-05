import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRateType } from 'app/shared/model/rate-type.model';
import { RateTypeService } from './rate-type.service';

@Component({
  templateUrl: './rate-type-delete-dialog.component.html',
})
export class RateTypeDeleteDialogComponent {
  rateType?: IRateType;

  constructor(protected rateTypeService: RateTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rateTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rateTypeListModification');
      this.activeModal.close();
    });
  }
}
