import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { PaymentGatewayConfigComponent } from 'app/entities/payment-gateway-config/payment-gateway-config.component';
import { PaymentGatewayConfigService } from 'app/entities/payment-gateway-config/payment-gateway-config.service';
import { PaymentGatewayConfig } from 'app/shared/model/payment-gateway-config.model';

describe('Component Tests', () => {
  describe('PaymentGatewayConfig Management Component', () => {
    let comp: PaymentGatewayConfigComponent;
    let fixture: ComponentFixture<PaymentGatewayConfigComponent>;
    let service: PaymentGatewayConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PaymentGatewayConfigComponent],
      })
        .overrideTemplate(PaymentGatewayConfigComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentGatewayConfigComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentGatewayConfigService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PaymentGatewayConfig(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.paymentGatewayConfigs && comp.paymentGatewayConfigs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
