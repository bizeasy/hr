import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { PerfReviewItemComponent } from 'app/entities/perf-review-item/perf-review-item.component';
import { PerfReviewItemService } from 'app/entities/perf-review-item/perf-review-item.service';
import { PerfReviewItem } from 'app/shared/model/perf-review-item.model';

describe('Component Tests', () => {
  describe('PerfReviewItem Management Component', () => {
    let comp: PerfReviewItemComponent;
    let fixture: ComponentFixture<PerfReviewItemComponent>;
    let service: PerfReviewItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PerfReviewItemComponent],
      })
        .overrideTemplate(PerfReviewItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerfReviewItemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerfReviewItemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PerfReviewItem(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.perfReviewItems && comp.perfReviewItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
