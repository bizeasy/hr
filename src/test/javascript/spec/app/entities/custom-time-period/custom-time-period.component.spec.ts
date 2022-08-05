import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { CustomTimePeriodComponent } from 'app/entities/custom-time-period/custom-time-period.component';
import { CustomTimePeriodService } from 'app/entities/custom-time-period/custom-time-period.service';
import { CustomTimePeriod } from 'app/shared/model/custom-time-period.model';

describe('Component Tests', () => {
  describe('CustomTimePeriod Management Component', () => {
    let comp: CustomTimePeriodComponent;
    let fixture: ComponentFixture<CustomTimePeriodComponent>;
    let service: CustomTimePeriodService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [CustomTimePeriodComponent],
      })
        .overrideTemplate(CustomTimePeriodComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomTimePeriodComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomTimePeriodService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CustomTimePeriod(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.customTimePeriods && comp.customTimePeriods[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
