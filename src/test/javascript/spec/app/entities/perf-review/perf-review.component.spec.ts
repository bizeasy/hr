import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { PerfReviewComponent } from 'app/entities/perf-review/perf-review.component';
import { PerfReviewService } from 'app/entities/perf-review/perf-review.service';
import { PerfReview } from 'app/shared/model/perf-review.model';

describe('Component Tests', () => {
  describe('PerfReview Management Component', () => {
    let comp: PerfReviewComponent;
    let fixture: ComponentFixture<PerfReviewComponent>;
    let service: PerfReviewService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PerfReviewComponent],
      })
        .overrideTemplate(PerfReviewComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerfReviewComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerfReviewService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PerfReview(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.perfReviews && comp.perfReviews[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
