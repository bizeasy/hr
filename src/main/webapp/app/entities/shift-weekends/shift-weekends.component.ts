import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IShiftWeekends } from 'app/shared/model/shift-weekends.model';
import { ShiftWeekendsService } from './shift-weekends.service';
import { ShiftWeekendsDeleteDialogComponent } from './shift-weekends-delete-dialog.component';

@Component({
  selector: 'sys-shift-weekends',
  templateUrl: './shift-weekends.component.html',
})
export class ShiftWeekendsComponent implements OnInit, OnDestroy {
  shiftWeekends?: IShiftWeekends[];
  eventSubscriber?: Subscription;

  constructor(
    protected shiftWeekendsService: ShiftWeekendsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.shiftWeekendsService.query().subscribe((res: HttpResponse<IShiftWeekends[]>) => (this.shiftWeekends = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInShiftWeekends();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IShiftWeekends): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInShiftWeekends(): void {
    this.eventSubscriber = this.eventManager.subscribe('shiftWeekendsListModification', () => this.loadAll());
  }

  delete(shiftWeekends: IShiftWeekends): void {
    const modalRef = this.modalService.open(ShiftWeekendsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.shiftWeekends = shiftWeekends;
  }
}
