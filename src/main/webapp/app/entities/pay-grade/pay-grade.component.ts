import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPayGrade } from 'app/shared/model/pay-grade.model';
import { PayGradeService } from './pay-grade.service';
import { PayGradeDeleteDialogComponent } from './pay-grade-delete-dialog.component';

@Component({
  selector: 'sys-pay-grade',
  templateUrl: './pay-grade.component.html',
})
export class PayGradeComponent implements OnInit, OnDestroy {
  payGrades?: IPayGrade[];
  eventSubscriber?: Subscription;

  constructor(protected payGradeService: PayGradeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.payGradeService.query().subscribe((res: HttpResponse<IPayGrade[]>) => (this.payGrades = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPayGrades();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPayGrade): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPayGrades(): void {
    this.eventSubscriber = this.eventManager.subscribe('payGradeListModification', () => this.loadAll());
  }

  delete(payGrade: IPayGrade): void {
    const modalRef = this.modalService.open(PayGradeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.payGrade = payGrade;
  }
}
