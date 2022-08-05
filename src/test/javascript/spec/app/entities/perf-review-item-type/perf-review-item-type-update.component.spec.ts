import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PerfReviewItemTypeUpdateComponent } from 'app/entities/perf-review-item-type/perf-review-item-type-update.component';
import { PerfReviewItemTypeService } from 'app/entities/perf-review-item-type/perf-review-item-type.service';
import { PerfReviewItemType } from 'app/shared/model/perf-review-item-type.model';

describe('Component Tests', () => {
  describe('PerfReviewItemType Management Update Component', () => {
    let comp: PerfReviewItemTypeUpdateComponent;
    let fixture: ComponentFixture<PerfReviewItemTypeUpdateComponent>;
    let service: PerfReviewItemTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PerfReviewItemTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PerfReviewItemTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerfReviewItemTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerfReviewItemTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PerfReviewItemType(123);
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
        const entity = new PerfReviewItemType();
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
