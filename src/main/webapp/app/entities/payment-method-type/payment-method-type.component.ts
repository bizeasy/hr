import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentMethodType } from 'app/shared/model/payment-method-type.model';
import { PaymentMethodTypeService } from './payment-method-type.service';
import { PaymentMethodTypeDeleteDialogComponent } from './payment-method-type-delete-dialog.component';

@Component({
  selector: 'sys-payment-method-type',
  templateUrl: './payment-method-type.component.html',
})
export class PaymentMethodTypeComponent implements OnInit, OnDestroy {
  paymentMethodTypes?: IPaymentMethodType[];
  eventSubscriber?: Subscription;

  constructor(
    protected paymentMethodTypeService: PaymentMethodTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.paymentMethodTypeService
      .query()
      .subscribe((res: HttpResponse<IPaymentMethodType[]>) => (this.paymentMethodTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPaymentMethodTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPaymentMethodType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPaymentMethodTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('paymentMethodTypeListModification', () => this.loadAll());
  }

  delete(paymentMethodType: IPaymentMethodType): void {
    const modalRef = this.modalService.open(PaymentMethodTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paymentMethodType = paymentMethodType;
  }
}
