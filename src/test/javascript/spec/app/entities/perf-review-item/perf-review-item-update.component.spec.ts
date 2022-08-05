import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PerfReviewItemUpdateComponent } from 'app/entities/perf-review-item/perf-review-item-update.component';
import { PerfReviewItemService } from 'app/entities/perf-review-item/perf-review-item.service';
import { PerfReviewItem } from 'app/shared/model/perf-review-item.model';

describe('Component Tests', () => {
  describe('PerfReviewItem Management Update Component', () => {
    let comp: PerfReviewItemUpdateComponent;
    let fixture: ComponentFixture<PerfReviewItemUpdateComponent>;
    let service: PerfReviewItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PerfReviewItemUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PerfReviewItemUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerfReviewItemUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerfReviewItemService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PerfReviewItem(123);
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
        const entity = new PerfReviewItem();
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
