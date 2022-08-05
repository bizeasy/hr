import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmplPositionTypeRate } from 'app/shared/model/empl-position-type-rate.model';
import { EmplPositionTypeRateService } from './empl-position-type-rate.service';

@Component({
  templateUrl: './empl-position-type-rate-delete-dialog.component.html',
})
export class EmplPositionTypeRateDeleteDialogComponent {
  emplPositionTypeRate?: IEmplPositionTypeRate;

  constructor(
    protected emplPositionTypeRateService: EmplPositionTypeRateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emplPositionTypeRateService.delete(id).subscribe(() => {
      this.eventManager.broadcast('emplPositionTypeRateListModification');
      this.activeModal.close();
    });
  }
}
