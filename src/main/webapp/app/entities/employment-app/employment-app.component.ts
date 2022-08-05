import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmploymentApp } from 'app/shared/model/employment-app.model';
import { EmploymentAppService } from './employment-app.service';
import { EmploymentAppDeleteDialogComponent } from './employment-app-delete-dialog.component';

@Component({
  selector: 'sys-employment-app',
  templateUrl: './employment-app.component.html',
})
export class EmploymentAppComponent implements OnInit, OnDestroy {
  employmentApps?: IEmploymentApp[];
  eventSubscriber?: Subscription;

  constructor(
    protected employmentAppService: EmploymentAppService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.employmentAppService.query().subscribe((res: HttpResponse<IEmploymentApp[]>) => (this.employmentApps = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEmploymentApps();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmploymentApp): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmploymentApps(): void {
    this.eventSubscriber = this.eventManager.subscribe('employmentAppListModification', () => this.loadAll());
  }

  delete(employmentApp: IEmploymentApp): void {
    const modalRef = this.modalService.open(EmploymentAppDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.employmentApp = employmentApp;
  }
}
