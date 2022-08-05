import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PerfRatingTypeUpdateComponent } from 'app/entities/perf-rating-type/perf-rating-type-update.component';
import { PerfRatingTypeService } from 'app/entities/perf-rating-type/perf-rating-type.service';
import { PerfRatingType } from 'app/shared/model/perf-rating-type.model';

describe('Component Tests', () => {
  describe('PerfRatingType Management Update Component', () => {
    let comp: PerfRatingTypeUpdateComponent;
    let fixture: ComponentFixture<PerfRatingTypeUpdateComponent>;
    let service: PerfRatingTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PerfRatingTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PerfRatingTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerfRatingTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerfRatingTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PerfRatingType(123);
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
        const entity = new PerfRatingType();
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
