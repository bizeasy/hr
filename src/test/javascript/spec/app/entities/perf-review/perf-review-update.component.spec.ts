import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PerfReviewUpdateComponent } from 'app/entities/perf-review/perf-review-update.component';
import { PerfReviewService } from 'app/entities/perf-review/perf-review.service';
import { PerfReview } from 'app/shared/model/perf-review.model';

describe('Component Tests', () => {
  describe('PerfReview Management Update Component', () => {
    let comp: PerfReviewUpdateComponent;
    let fixture: ComponentFixture<PerfReviewUpdateComponent>;
    let service: PerfReviewService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PerfReviewUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PerfReviewUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerfReviewUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerfReviewService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PerfReview(123);
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
        const entity = new PerfReview();
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
