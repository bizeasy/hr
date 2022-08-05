import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmployment } from 'app/shared/model/employment.model';
import { EmploymentService } from './employment.service';
import { EmploymentDeleteDialogComponent } from './employment-delete-dialog.component';

@Component({
  selector: 'sys-employment',
  templateUrl: './employment.component.html',
})
export class EmploymentComponent implements OnInit, OnDestroy {
  employments?: IEmployment[];
  eventSubscriber?: Subscription;

  constructor(protected employmentService: EmploymentService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.employmentService.query().subscribe((res: HttpResponse<IEmployment[]>) => (this.employments = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEmployments();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmployment): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmployments(): void {
    this.eventSubscriber = this.eventManager.subscribe('employmentListModification', () => this.loadAll());
  }

  delete(employment: IEmployment): void {
    const modalRef = this.modalService.open(EmploymentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.employment = employment;
  }
}
