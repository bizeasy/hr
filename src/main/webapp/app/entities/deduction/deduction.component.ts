import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeduction } from 'app/shared/model/deduction.model';
import { DeductionService } from './deduction.service';
import { DeductionDeleteDialogComponent } from './deduction-delete-dialog.component';

@Component({
  selector: 'sys-deduction',
  templateUrl: './deduction.component.html',
})
export class DeductionComponent implements OnInit, OnDestroy {
  deductions?: IDeduction[];
  eventSubscriber?: Subscription;

  constructor(protected deductionService: DeductionService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.deductionService.query().subscribe((res: HttpResponse<IDeduction[]>) => (this.deductions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDeductions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDeduction): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDeductions(): void {
    this.eventSubscriber = this.eventManager.subscribe('deductionListModification', () => this.loadAll());
  }

  delete(deduction: IDeduction): void {
    const modalRef = this.modalService.open(DeductionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deduction = deduction;
  }
}
