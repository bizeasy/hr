import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmplPositionTypeRate } from 'app/shared/model/empl-position-type-rate.model';
import { EmplPositionTypeRateService } from './empl-position-type-rate.service';
import { EmplPositionTypeRateDeleteDialogComponent } from './empl-position-type-rate-delete-dialog.component';

@Component({
  selector: 'sys-empl-position-type-rate',
  templateUrl: './empl-position-type-rate.component.html',
})
export class EmplPositionTypeRateComponent implements OnInit, OnDestroy {
  emplPositionTypeRates?: IEmplPositionTypeRate[];
  eventSubscriber?: Subscription;

  constructor(
    protected emplPositionTypeRateService: EmplPositionTypeRateService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.emplPositionTypeRateService
      .query()
      .subscribe((res: HttpResponse<IEmplPositionTypeRate[]>) => (this.emplPositionTypeRates = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEmplPositionTypeRates();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmplPositionTypeRate): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmplPositionTypeRates(): void {
    this.eventSubscriber = this.eventManager.subscribe('emplPositionTypeRateListModification', () => this.loadAll());
  }

  delete(emplPositionTypeRate: IEmplPositionTypeRate): void {
    const modalRef = this.modalService.open(EmplPositionTypeRateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.emplPositionTypeRate = emplPositionTypeRate;
  }
}
