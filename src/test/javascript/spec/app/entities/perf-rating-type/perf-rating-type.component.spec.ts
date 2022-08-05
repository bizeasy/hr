import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { PerfRatingTypeComponent } from 'app/entities/perf-rating-type/perf-rating-type.component';
import { PerfRatingTypeService } from 'app/entities/perf-rating-type/perf-rating-type.service';
import { PerfRatingType } from 'app/shared/model/perf-rating-type.model';

describe('Component Tests', () => {
  describe('PerfRatingType Management Component', () => {
    let comp: PerfRatingTypeComponent;
    let fixture: ComponentFixture<PerfRatingTypeComponent>;
    let service: PerfRatingTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PerfRatingTypeComponent],
      })
        .overrideTemplate(PerfRatingTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerfRatingTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerfRatingTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PerfRatingType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.perfRatingTypes && comp.perfRatingTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
