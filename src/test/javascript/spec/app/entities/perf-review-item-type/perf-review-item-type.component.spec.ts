import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { PerfReviewItemTypeComponent } from 'app/entities/perf-review-item-type/perf-review-item-type.component';
import { PerfReviewItemTypeService } from 'app/entities/perf-review-item-type/perf-review-item-type.service';
import { PerfReviewItemType } from 'app/shared/model/perf-review-item-type.model';

describe('Component Tests', () => {
  describe('PerfReviewItemType Management Component', () => {
    let comp: PerfReviewItemTypeComponent;
    let fixture: ComponentFixture<PerfReviewItemTypeComponent>;
    let service: PerfReviewItemTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PerfReviewItemTypeComponent],
      })
        .overrideTemplate(PerfReviewItemTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerfReviewItemTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerfReviewItemTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PerfReviewItemType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.perfReviewItemTypes && comp.perfReviewItemTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
