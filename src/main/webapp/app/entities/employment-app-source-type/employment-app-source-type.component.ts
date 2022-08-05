import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmploymentAppSourceType } from 'app/shared/model/employment-app-source-type.model';
import { EmploymentAppSourceTypeService } from './employment-app-source-type.service';
import { EmploymentAppSourceTypeDeleteDialogComponent } from './employment-app-source-type-delete-dialog.component';

@Component({
  selector: 'sys-employment-app-source-type',
  templateUrl: './employment-app-source-type.component.html',
})
export class EmploymentAppSourceTypeComponent implements OnInit, OnDestroy {
  employmentAppSourceTypes?: IEmploymentAppSourceType[];
  eventSubscriber?: Subscription;

  constructor(
    protected employmentAppSourceTypeService: EmploymentAppSourceTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.employmentAppSourceTypeService
      .query()
      .subscribe((res: HttpResponse<IEmploymentAppSourceType[]>) => (this.employmentAppSourceTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEmploymentAppSourceTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmploymentAppSourceType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmploymentAppSourceTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('employmentAppSourceTypeListModification', () => this.loadAll());
  }

  delete(employmentAppSourceType: IEmploymentAppSourceType): void {
    const modalRef = this.modalService.open(EmploymentAppSourceTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.employmentAppSourceType = employmentAppSourceType;
  }
}
