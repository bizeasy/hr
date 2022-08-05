import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { PaymentGatewayResponseComponent } from 'app/entities/payment-gateway-response/payment-gateway-response.component';
import { PaymentGatewayResponseService } from 'app/entities/payment-gateway-response/payment-gateway-response.service';
import { PaymentGatewayResponse } from 'app/shared/model/payment-gateway-response.model';

describe('Component Tests', () => {
  describe('PaymentGatewayResponse Management Component', () => {
    let comp: PaymentGatewayResponseComponent;
    let fixture: ComponentFixture<PaymentGatewayResponseComponent>;
    let service: PaymentGatewayResponseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PaymentGatewayResponseComponent],
      })
        .overrideTemplate(PaymentGatewayResponseComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentGatewayResponseComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentGatewayResponseService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PaymentGatewayResponse(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.paymentGatewayResponses && comp.paymentGatewayResponses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
