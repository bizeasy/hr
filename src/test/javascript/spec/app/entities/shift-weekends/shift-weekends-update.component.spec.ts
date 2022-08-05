import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ShiftWeekendsUpdateComponent } from 'app/entities/shift-weekends/shift-weekends-update.component';
import { ShiftWeekendsService } from 'app/entities/shift-weekends/shift-weekends.service';
import { ShiftWeekends } from 'app/shared/model/shift-weekends.model';

describe('Component Tests', () => {
  describe('ShiftWeekends Management Update Component', () => {
    let comp: ShiftWeekendsUpdateComponent;
    let fixture: ComponentFixture<ShiftWeekendsUpdateComponent>;
    let service: ShiftWeekendsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ShiftWeekendsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ShiftWeekendsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShiftWeekendsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShiftWeekendsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ShiftWeekends(123);
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
        const entity = new ShiftWeekends();
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
