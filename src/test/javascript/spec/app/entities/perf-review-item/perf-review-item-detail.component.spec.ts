import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PerfReviewItemDetailComponent } from 'app/entities/perf-review-item/perf-review-item-detail.component';
import { PerfReviewItem } from 'app/shared/model/perf-review-item.model';

describe('Component Tests', () => {
  describe('PerfReviewItem Management Detail Component', () => {
    let comp: PerfReviewItemDetailComponent;
    let fixture: ComponentFixture<PerfReviewItemDetailComponent>;
    const route = ({ data: of({ perfReviewItem: new PerfReviewItem(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PerfReviewItemDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PerfReviewItemDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PerfReviewItemDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load perfReviewItem on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.perfReviewItem).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
