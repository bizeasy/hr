import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentGatewayConfig } from 'app/shared/model/payment-gateway-config.model';
import { PaymentGatewayConfigService } from './payment-gateway-config.service';
import { PaymentGatewayConfigDeleteDialogComponent } from './payment-gateway-config-delete-dialog.component';

@Component({
  selector: 'sys-payment-gateway-config',
  templateUrl: './payment-gateway-config.component.html',
})
export class PaymentGatewayConfigComponent implements OnInit, OnDestroy {
  paymentGatewayConfigs?: IPaymentGatewayConfig[];
  eventSubscriber?: Subscription;

  constructor(
    protected paymentGatewayConfigService: PaymentGatewayConfigService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.paymentGatewayConfigService
      .query()
      .subscribe((res: HttpResponse<IPaymentGatewayConfig[]>) => (this.paymentGatewayConfigs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPaymentGatewayConfigs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPaymentGatewayConfig): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPaymentGatewayConfigs(): void {
    this.eventSubscriber = this.eventManager.subscribe('paymentGatewayConfigListModification', () => this.loadAll());
  }

  delete(paymentGatewayConfig: IPaymentGatewayConfig): void {
    const modalRef = this.modalService.open(PaymentGatewayConfigDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paymentGatewayConfig = paymentGatewayConfig;
  }
}
