import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PerfReviewItemTypeDetailComponent } from 'app/entities/perf-review-item-type/perf-review-item-type-detail.component';
import { PerfReviewItemType } from 'app/shared/model/perf-review-item-type.model';

describe('Component Tests', () => {
  describe('PerfReviewItemType Management Detail Component', () => {
    let comp: PerfReviewItemTypeDetailComponent;
    let fixture: ComponentFixture<PerfReviewItemTypeDetailComponent>;
    const route = ({ data: of({ perfReviewItemType: new PerfReviewItemType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PerfReviewItemTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PerfReviewItemTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PerfReviewItemTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load perfReviewItemType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.perfReviewItemType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
