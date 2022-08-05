import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHolidayType } from 'app/shared/model/holiday-type.model';
import { HolidayTypeService } from './holiday-type.service';
import { HolidayTypeDeleteDialogComponent } from './holiday-type-delete-dialog.component';

@Component({
  selector: 'sys-holiday-type',
  templateUrl: './holiday-type.component.html',
})
export class HolidayTypeComponent implements OnInit, OnDestroy {
  holidayTypes?: IHolidayType[];
  eventSubscriber?: Subscription;

  constructor(
    protected holidayTypeService: HolidayTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.holidayTypeService.query().subscribe((res: HttpResponse<IHolidayType[]>) => (this.holidayTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInHolidayTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IHolidayType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInHolidayTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('holidayTypeListModification', () => this.loadAll());
  }

  delete(holidayType: IHolidayType): void {
    const modalRef = this.modalService.open(HolidayTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.holidayType = holidayType;
  }
}
