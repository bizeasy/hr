import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFacilityUsageLog } from 'app/shared/model/facility-usage-log.model';
import { FacilityUsageLogService } from './facility-usage-log.service';
import { FacilityUsageLogDeleteDialogComponent } from './facility-usage-log-delete-dialog.component';

@Component({
  selector: 'sys-facility-usage-log',
  templateUrl: './facility-usage-log.component.html',
})
export class FacilityUsageLogComponent implements OnInit, OnDestroy {
  facilityUsageLogs?: IFacilityUsageLog[];
  eventSubscriber?: Subscription;

  constructor(
    protected facilityUsageLogService: FacilityUsageLogService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.facilityUsageLogService.query().subscribe((res: HttpResponse<IFacilityUsageLog[]>) => (this.facilityUsageLogs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFacilityUsageLogs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFacilityUsageLog): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFacilityUsageLogs(): void {
    this.eventSubscriber = this.eventManager.subscribe('facilityUsageLogListModification', () => this.loadAll());
  }

  delete(facilityUsageLog: IFacilityUsageLog): void {
    const modalRef = this.modalService.open(FacilityUsageLogDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.facilityUsageLog = facilityUsageLog;
  }
}
