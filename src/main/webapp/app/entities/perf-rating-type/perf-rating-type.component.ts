import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPerfRatingType } from 'app/shared/model/perf-rating-type.model';
import { PerfRatingTypeService } from './perf-rating-type.service';
import { PerfRatingTypeDeleteDialogComponent } from './perf-rating-type-delete-dialog.component';

@Component({
  selector: 'sys-perf-rating-type',
  templateUrl: './perf-rating-type.component.html',
})
export class PerfRatingTypeComponent implements OnInit, OnDestroy {
  perfRatingTypes?: IPerfRatingType[];
  eventSubscriber?: Subscription;

  constructor(
    protected perfRatingTypeService: PerfRatingTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.perfRatingTypeService.query().subscribe((res: HttpResponse<IPerfRatingType[]>) => (this.perfRatingTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPerfRatingTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPerfRatingType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPerfRatingTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('perfRatingTypeListModification', () => this.loadAll());
  }

  delete(perfRatingType: IPerfRatingType): void {
    const modalRef = this.modalService.open(PerfRatingTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.perfRatingType = perfRatingType;
  }
}
