import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ShiftHolidaysUpdateComponent } from 'app/entities/shift-holidays/shift-holidays-update.component';
import { ShiftHolidaysService } from 'app/entities/shift-holidays/shift-holidays.service';
import { ShiftHolidays } from 'app/shared/model/shift-holidays.model';

describe('Component Tests', () => {
  describe('ShiftHolidays Management Update Component', () => {
    let comp: ShiftHolidaysUpdateComponent;
    let fixture: ComponentFixture<ShiftHolidaysUpdateComponent>;
    let service: ShiftHolidaysService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ShiftHolidaysUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ShiftHolidaysUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShiftHolidaysUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShiftHolidaysService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ShiftHolidays(123);
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
        const entity = new ShiftHolidays();
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
