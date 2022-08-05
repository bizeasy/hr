import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PaymentApplicationDetailComponent } from 'app/entities/payment-application/payment-application-detail.component';
import { PaymentApplication } from 'app/shared/model/payment-application.model';

describe('Component Tests', () => {
  describe('PaymentApplication Management Detail Component', () => {
    let comp: PaymentApplicationDetailComponent;
    let fixture: ComponentFixture<PaymentApplicationDetailComponent>;
    const route = ({ data: of({ paymentApplication: new PaymentApplication(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PaymentApplicationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PaymentApplicationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentApplicationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load paymentApplication on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paymentApplication).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
