import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocationType } from 'app/shared/model/location-type.model';
import { LocationTypeService } from './location-type.service';

@Component({
  templateUrl: './location-type-delete-dialog.component.html',
})
export class LocationTypeDeleteDialogComponent {
  locationType?: ILocationType;

  constructor(
    protected locationTypeService: LocationTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.locationTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('locationTypeListModification');
      this.activeModal.close();
    });
  }
}
