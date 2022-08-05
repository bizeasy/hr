import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmployment } from 'app/shared/model/employment.model';
import { EmploymentService } from './employment.service';

@Component({
  templateUrl: './employment-delete-dialog.component.html',
})
export class EmploymentDeleteDialogComponent {
  employment?: IEmployment;

  constructor(
    protected employmentService: EmploymentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employmentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('employmentListModification');
      this.activeModal.close();
    });
  }
}
