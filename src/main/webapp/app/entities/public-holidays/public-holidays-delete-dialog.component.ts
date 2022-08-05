import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPublicHolidays } from 'app/shared/model/public-holidays.model';
import { PublicHolidaysService } from './public-holidays.service';

@Component({
  templateUrl: './public-holidays-delete-dialog.component.html',
})
export class PublicHolidaysDeleteDialogComponent {
  publicHolidays?: IPublicHolidays;

  constructor(
    protected publicHolidaysService: PublicHolidaysService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.publicHolidaysService.delete(id).subscribe(() => {
      this.eventManager.broadcast('publicHolidaysListModification');
      this.activeModal.close();
    });
  }
}
