import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PaymentGatewayConfigDetailComponent } from 'app/entities/payment-gateway-config/payment-gateway-config-detail.component';
import { PaymentGatewayConfig } from 'app/shared/model/payment-gateway-config.model';

describe('Component Tests', () => {
  describe('PaymentGatewayConfig Management Detail Component', () => {
    let comp: PaymentGatewayConfigDetailComponent;
    let fixture: ComponentFixture<PaymentGatewayConfigDetailComponent>;
    const route = ({ data: of({ paymentGatewayConfig: new PaymentGatewayConfig(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PaymentGatewayConfigDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PaymentGatewayConfigDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentGatewayConfigDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load paymentGatewayConfig on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paymentGatewayConfig).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
