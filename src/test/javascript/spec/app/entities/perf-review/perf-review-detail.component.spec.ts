import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PerfReviewDetailComponent } from 'app/entities/perf-review/perf-review-detail.component';
import { PerfReview } from 'app/shared/model/perf-review.model';

describe('Component Tests', () => {
  describe('PerfReview Management Detail Component', () => {
    let comp: PerfReviewDetailComponent;
    let fixture: ComponentFixture<PerfReviewDetailComponent>;
    const route = ({ data: of({ perfReview: new PerfReview(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PerfReviewDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PerfReviewDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PerfReviewDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load perfReview on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.perfReview).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
