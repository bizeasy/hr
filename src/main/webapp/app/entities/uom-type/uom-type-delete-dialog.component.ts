import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUomType } from 'app/shared/model/uom-type.model';
import { UomTypeService } from './uom-type.service';

@Component({
  templateUrl: './uom-type-delete-dialog.component.html',
})
export class UomTypeDeleteDialogComponent {
  uomType?: IUomType;

  constructor(protected uomTypeService: UomTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.uomTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('uomTypeListModification');
      this.activeModal.close();
    });
  }
}
