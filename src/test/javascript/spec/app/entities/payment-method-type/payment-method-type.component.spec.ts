import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { PaymentMethodTypeComponent } from 'app/entities/payment-method-type/payment-method-type.component';
import { PaymentMethodTypeService } from 'app/entities/payment-method-type/payment-method-type.service';
import { PaymentMethodType } from 'app/shared/model/payment-method-type.model';

describe('Component Tests', () => {
  describe('PaymentMethodType Management Component', () => {
    let comp: PaymentMethodTypeComponent;
    let fixture: ComponentFixture<PaymentMethodTypeComponent>;
    let service: PaymentMethodTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PaymentMethodTypeComponent],
      })
        .overrideTemplate(PaymentMethodTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentMethodTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentMethodTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PaymentMethodType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.paymentMethodTypes && comp.paymentMethodTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
