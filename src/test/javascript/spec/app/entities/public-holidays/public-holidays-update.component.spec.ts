import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PublicHolidaysUpdateComponent } from 'app/entities/public-holidays/public-holidays-update.component';
import { PublicHolidaysService } from 'app/entities/public-holidays/public-holidays.service';
import { PublicHolidays } from 'app/shared/model/public-holidays.model';

describe('Component Tests', () => {
  describe('PublicHolidays Management Update Component', () => {
    let comp: PublicHolidaysUpdateComponent;
    let fixture: ComponentFixture<PublicHolidaysUpdateComponent>;
    let service: PublicHolidaysService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PublicHolidaysUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PublicHolidaysUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PublicHolidaysUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PublicHolidaysService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PublicHolidays(123);
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
        const entity = new PublicHolidays();
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
