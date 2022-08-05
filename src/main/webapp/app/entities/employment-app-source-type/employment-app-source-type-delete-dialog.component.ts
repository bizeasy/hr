import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmploymentAppSourceType } from 'app/shared/model/employment-app-source-type.model';
import { EmploymentAppSourceTypeService } from './employment-app-source-type.service';

@Component({
  templateUrl: './employment-app-source-type-delete-dialog.component.html',
})
export class EmploymentAppSourceTypeDeleteDialogComponent {
  employmentAppSourceType?: IEmploymentAppSourceType;

  constructor(
    protected employmentAppSourceTypeService: EmploymentAppSourceTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employmentAppSourceTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('employmentAppSourceTypeListModification');
      this.activeModal.close();
    });
  }
}
