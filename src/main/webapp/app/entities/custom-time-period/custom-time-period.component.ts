import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomTimePeriod } from 'app/shared/model/custom-time-period.model';
import { CustomTimePeriodService } from './custom-time-period.service';
import { CustomTimePeriodDeleteDialogComponent } from './custom-time-period-delete-dialog.component';

@Component({
  selector: 'sys-custom-time-period',
  templateUrl: './custom-time-period.component.html',
})
export class CustomTimePeriodComponent implements OnInit, OnDestroy {
  customTimePeriods?: ICustomTimePeriod[];
  eventSubscriber?: Subscription;

  constructor(
    protected customTimePeriodService: CustomTimePeriodService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.customTimePeriodService.query().subscribe((res: HttpResponse<ICustomTimePeriod[]>) => (this.customTimePeriods = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCustomTimePeriods();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICustomTimePeriod): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCustomTimePeriods(): void {
    this.eventSubscriber = this.eventManager.subscribe('customTimePeriodListModification', () => this.loadAll());
  }

  delete(customTimePeriod: ICustomTimePeriod): void {
    const modalRef = this.modalService.open(CustomTimePeriodDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.customTimePeriod = customTimePeriod;
  }
}
