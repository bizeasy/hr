import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEquipmentUsageLog } from 'app/shared/model/equipment-usage-log.model';
import { EquipmentUsageLogService } from './equipment-usage-log.service';

@Component({
  templateUrl: './equipment-usage-log-delete-dialog.component.html',
})
export class EquipmentUsageLogDeleteDialogComponent {
  equipmentUsageLog?: IEquipmentUsageLog;

  constructor(
    protected equipmentUsageLogService: EquipmentUsageLogService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.equipmentUsageLogService.delete(id).subscribe(() => {
      this.eventManager.broadcast('equipmentUsageLogListModification');
      this.activeModal.close();
    });
  }
}
