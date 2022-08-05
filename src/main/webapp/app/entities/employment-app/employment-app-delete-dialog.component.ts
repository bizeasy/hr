import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmploymentApp } from 'app/shared/model/employment-app.model';
import { EmploymentAppService } from './employment-app.service';

@Component({
  templateUrl: './employment-app-delete-dialog.component.html',
})
export class EmploymentAppDeleteDialogComponent {
  employmentApp?: IEmploymentApp;

  constructor(
    protected employmentAppService: EmploymentAppService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employmentAppService.delete(id).subscribe(() => {
      this.eventManager.broadcast('employmentAppListModification');
      this.activeModal.close();
    });
  }
}
