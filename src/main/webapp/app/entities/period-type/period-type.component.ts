import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPeriodType } from 'app/shared/model/period-type.model';
import { PeriodTypeService } from './period-type.service';
import { PeriodTypeDeleteDialogComponent } from './period-type-delete-dialog.component';

@Component({
  selector: 'sys-period-type',
  templateUrl: './period-type.component.html',
})
export class PeriodTypeComponent implements OnInit, OnDestroy {
  periodTypes?: IPeriodType[];
  eventSubscriber?: Subscription;

  constructor(protected periodTypeService: PeriodTypeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.periodTypeService.query().subscribe((res: HttpResponse<IPeriodType[]>) => (this.periodTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPeriodTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPeriodType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPeriodTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('periodTypeListModification', () => this.loadAll());
  }

  delete(periodType: IPeriodType): void {
    const modalRef = this.modalService.open(PeriodTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.periodType = periodType;
  }
}
