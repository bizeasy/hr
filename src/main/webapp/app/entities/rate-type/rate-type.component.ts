import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRateType } from 'app/shared/model/rate-type.model';
import { RateTypeService } from './rate-type.service';
import { RateTypeDeleteDialogComponent } from './rate-type-delete-dialog.component';

@Component({
  selector: 'sys-rate-type',
  templateUrl: './rate-type.component.html',
})
export class RateTypeComponent implements OnInit, OnDestroy {
  rateTypes?: IRateType[];
  eventSubscriber?: Subscription;

  constructor(protected rateTypeService: RateTypeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.rateTypeService.query().subscribe((res: HttpResponse<IRateType[]>) => (this.rateTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRateTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRateType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRateTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('rateTypeListModification', () => this.loadAll());
  }

  delete(rateType: IRateType): void {
    const modalRef = this.modalService.open(RateTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rateType = rateType;
  }
}
