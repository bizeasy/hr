import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PayrollPreferenceDetailComponent } from 'app/entities/payroll-preference/payroll-preference-detail.component';
import { PayrollPreference } from 'app/shared/model/payroll-preference.model';

describe('Component Tests', () => {
  describe('PayrollPreference Management Detail Component', () => {
    let comp: PayrollPreferenceDetailComponent;
    let fixture: ComponentFixture<PayrollPreferenceDetailComponent>;
    const route = ({ data: of({ payrollPreference: new PayrollPreference(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PayrollPreferenceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PayrollPreferenceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PayrollPreferenceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load payrollPreference on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.payrollPreference).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
