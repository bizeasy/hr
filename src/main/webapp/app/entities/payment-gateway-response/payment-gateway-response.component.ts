import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentGatewayResponse } from 'app/shared/model/payment-gateway-response.model';
import { PaymentGatewayResponseService } from './payment-gateway-response.service';
import { PaymentGatewayResponseDeleteDialogComponent } from './payment-gateway-response-delete-dialog.component';

@Component({
  selector: 'sys-payment-gateway-response',
  templateUrl: './payment-gateway-response.component.html',
})
export class PaymentGatewayResponseComponent implements OnInit, OnDestroy {
  paymentGatewayResponses?: IPaymentGatewayResponse[];
  eventSubscriber?: Subscription;

  constructor(
    protected paymentGatewayResponseService: PaymentGatewayResponseService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.paymentGatewayResponseService
      .query()
      .subscribe((res: HttpResponse<IPaymentGatewayResponse[]>) => (this.paymentGatewayResponses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPaymentGatewayResponses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPaymentGatewayResponse): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPaymentGatewayResponses(): void {
    this.eventSubscriber = this.eventManager.subscribe('paymentGatewayResponseListModification', () => this.loadAll());
  }

  delete(paymentGatewayResponse: IPaymentGatewayResponse): void {
    const modalRef = this.modalService.open(PaymentGatewayResponseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paymentGatewayResponse = paymentGatewayResponse;
  }
}
