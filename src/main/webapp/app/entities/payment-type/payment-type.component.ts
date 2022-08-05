import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentType } from 'app/shared/model/payment-type.model';
import { PaymentTypeService } from './payment-type.service';
import { PaymentTypeDeleteDialogComponent } from './payment-type-delete-dialog.component';

@Component({
  selector: 'sys-payment-type',
  templateUrl: './payment-type.component.html',
})
export class PaymentTypeComponent implements OnInit, OnDestroy {
  paymentTypes?: IPaymentType[];
  eventSubscriber?: Subscription;

  constructor(
    protected paymentTypeService: PaymentTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.paymentTypeService.query().subscribe((res: HttpResponse<IPaymentType[]>) => (this.paymentTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPaymentTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPaymentType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPaymentTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('paymentTypeListModification', () => this.loadAll());
  }

  delete(paymentType: IPaymentType): void {
    const modalRef = this.modalService.open(PaymentTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paymentType = paymentType;
  }
}
