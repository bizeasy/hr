import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFacilityEquipment } from 'app/shared/model/facility-equipment.model';
import { FacilityEquipmentService } from './facility-equipment.service';

@Component({
  templateUrl: './facility-equipment-delete-dialog.component.html',
})
export class FacilityEquipmentDeleteDialogComponent {
  facilityEquipment?: IFacilityEquipment;

  constructor(
    protected facilityEquipmentService: FacilityEquipmentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.facilityEquipmentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('facilityEquipmentListModification');
      this.activeModal.close();
    });
  }
}
