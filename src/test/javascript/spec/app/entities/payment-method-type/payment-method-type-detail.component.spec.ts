import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PaymentMethodTypeDetailComponent } from 'app/entities/payment-method-type/payment-method-type-detail.component';
import { PaymentMethodType } from 'app/shared/model/payment-method-type.model';

describe('Component Tests', () => {
  describe('PaymentMethodType Management Detail Component', () => {
    let comp: PaymentMethodTypeDetailComponent;
    let fixture: ComponentFixture<PaymentMethodTypeDetailComponent>;
    const route = ({ data: of({ paymentMethodType: new PaymentMethodType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PaymentMethodTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PaymentMethodTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentMethodTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load paymentMethodType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paymentMethodType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
