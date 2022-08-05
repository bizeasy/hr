import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { PaymentTypeComponent } from 'app/entities/payment-type/payment-type.component';
import { PaymentTypeService } from 'app/entities/payment-type/payment-type.service';
import { PaymentType } from 'app/shared/model/payment-type.model';

describe('Component Tests', () => {
  describe('PaymentType Management Component', () => {
    let comp: PaymentTypeComponent;
    let fixture: ComponentFixture<PaymentTypeComponent>;
    let service: PaymentTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PaymentTypeComponent],
      })
        .overrideTemplate(PaymentTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PaymentType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.paymentTypes && comp.paymentTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
