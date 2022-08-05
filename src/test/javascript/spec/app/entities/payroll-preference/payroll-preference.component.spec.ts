import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { PayrollPreferenceComponent } from 'app/entities/payroll-preference/payroll-preference.component';
import { PayrollPreferenceService } from 'app/entities/payroll-preference/payroll-preference.service';
import { PayrollPreference } from 'app/shared/model/payroll-preference.model';

describe('Component Tests', () => {
  describe('PayrollPreference Management Component', () => {
    let comp: PayrollPreferenceComponent;
    let fixture: ComponentFixture<PayrollPreferenceComponent>;
    let service: PayrollPreferenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PayrollPreferenceComponent],
      })
        .overrideTemplate(PayrollPreferenceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PayrollPreferenceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PayrollPreferenceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PayrollPreference(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.payrollPreferences && comp.payrollPreferences[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
