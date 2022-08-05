import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITimeSheet } from 'app/shared/model/time-sheet.model';
import { TimeSheetService } from './time-sheet.service';
import { TimeSheetDeleteDialogComponent } from './time-sheet-delete-dialog.component';

@Component({
  selector: 'sys-time-sheet',
  templateUrl: './time-sheet.component.html',
})
export class TimeSheetComponent implements OnInit, OnDestroy {
  timeSheets?: ITimeSheet[];
  eventSubscriber?: Subscription;

  constructor(protected timeSheetService: TimeSheetService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.timeSheetService.query().subscribe((res: HttpResponse<ITimeSheet[]>) => (this.timeSheets = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTimeSheets();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITimeSheet): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTimeSheets(): void {
    this.eventSubscriber = this.eventManager.subscribe('timeSheetListModification', () => this.loadAll());
  }

  delete(timeSheet: ITimeSheet): void {
    const modalRef = this.modalService.open(TimeSheetDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.timeSheet = timeSheet;
  }
}
