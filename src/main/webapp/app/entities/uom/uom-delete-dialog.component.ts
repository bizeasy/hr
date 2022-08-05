import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUom } from 'app/shared/model/uom.model';
import { UomService } from './uom.service';

@Component({
  templateUrl: './uom-delete-dialog.component.html',
})
export class UomDeleteDialogComponent {
  uom?: IUom;

  constructor(protected uomService: UomService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.uomService.delete(id).subscribe(() => {
      this.eventManager.broadcast('uomListModification');
      this.activeModal.close();
    });
  }
}
