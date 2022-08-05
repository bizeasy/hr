import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PaymentGatewayResponseDetailComponent } from 'app/entities/payment-gateway-response/payment-gateway-response-detail.component';
import { PaymentGatewayResponse } from 'app/shared/model/payment-gateway-response.model';

describe('Component Tests', () => {
  describe('PaymentGatewayResponse Management Detail Component', () => {
    let comp: PaymentGatewayResponseDetailComponent;
    let fixture: ComponentFixture<PaymentGatewayResponseDetailComponent>;
    const route = ({ data: of({ paymentGatewayResponse: new PaymentGatewayResponse(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PaymentGatewayResponseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PaymentGatewayResponseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentGatewayResponseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load paymentGatewayResponse on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paymentGatewayResponse).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
