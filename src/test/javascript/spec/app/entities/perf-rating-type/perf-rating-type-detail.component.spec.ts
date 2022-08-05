import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PerfRatingTypeDetailComponent } from 'app/entities/perf-rating-type/perf-rating-type-detail.component';
import { PerfRatingType } from 'app/shared/model/perf-rating-type.model';

describe('Component Tests', () => {
  describe('PerfRatingType Management Detail Component', () => {
    let comp: PerfRatingTypeDetailComponent;
    let fixture: ComponentFixture<PerfRatingTypeDetailComponent>;
    const route = ({ data: of({ perfRatingType: new PerfRatingType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PerfRatingTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PerfRatingTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PerfRatingTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load perfRatingType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.perfRatingType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
