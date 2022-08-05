import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IShiftHolidays } from 'app/shared/model/shift-holidays.model';
import { ShiftHolidaysService } from './shift-holidays.service';
import { ShiftHolidaysDeleteDialogComponent } from './shift-holidays-delete-dialog.component';

@Component({
  selector: 'sys-shift-holidays',
  templateUrl: './shift-holidays.component.html',
})
export class ShiftHolidaysComponent implements OnInit, OnDestroy {
  shiftHolidays?: IShiftHolidays[];
  eventSubscriber?: Subscription;

  constructor(
    protected shiftHolidaysService: ShiftHolidaysService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.shiftHolidaysService.query().subscribe((res: HttpResponse<IShiftHolidays[]>) => (this.shiftHolidays = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInShiftHolidays();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IShiftHolidays): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInShiftHolidays(): void {
    this.eventSubscriber = this.eventManager.subscribe('shiftHolidaysListModification', () => this.loadAll());
  }

  delete(shiftHolidays: IShiftHolidays): void {
    const modalRef = this.modalService.open(ShiftHolidaysDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.shiftHolidays = shiftHolidays;
  }
}
