import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPublicHolidays } from 'app/shared/model/public-holidays.model';
import { PublicHolidaysService } from './public-holidays.service';
import { PublicHolidaysDeleteDialogComponent } from './public-holidays-delete-dialog.component';

@Component({
  selector: 'sys-public-holidays',
  templateUrl: './public-holidays.component.html',
})
export class PublicHolidaysComponent implements OnInit, OnDestroy {
  publicHolidays?: IPublicHolidays[];
  eventSubscriber?: Subscription;

  constructor(
    protected publicHolidaysService: PublicHolidaysService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.publicHolidaysService.query().subscribe((res: HttpResponse<IPublicHolidays[]>) => (this.publicHolidays = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPublicHolidays();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPublicHolidays): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPublicHolidays(): void {
    this.eventSubscriber = this.eventManager.subscribe('publicHolidaysListModification', () => this.loadAll());
  }

  delete(publicHolidays: IPublicHolidays): void {
    const modalRef = this.modalService.open(PublicHolidaysDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.publicHolidays = publicHolidays;
  }
}
