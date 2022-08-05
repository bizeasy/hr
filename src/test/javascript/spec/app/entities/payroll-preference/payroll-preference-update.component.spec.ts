import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PayrollPreferenceUpdateComponent } from 'app/entities/payroll-preference/payroll-preference-update.component';
import { PayrollPreferenceService } from 'app/entities/payroll-preference/payroll-preference.service';
import { PayrollPreference } from 'app/shared/model/payroll-preference.model';

describe('Component Tests', () => {
  describe('PayrollPreference Management Update Component', () => {
    let comp: PayrollPreferenceUpdateComponent;
    let fixture: ComponentFixture<PayrollPreferenceUpdateComponent>;
    let service: PayrollPreferenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PayrollPreferenceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PayrollPreferenceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PayrollPreferenceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PayrollPreferenceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PayrollPreference(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PayrollPreference();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
