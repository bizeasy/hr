import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { HolidayTypeComponent } from 'app/entities/holiday-type/holiday-type.component';
import { HolidayTypeService } from 'app/entities/holiday-type/holiday-type.service';
import { HolidayType } from 'app/shared/model/holiday-type.model';

describe('Component Tests', () => {
  describe('HolidayType Management Component', () => {
    let comp: HolidayTypeComponent;
    let fixture: ComponentFixture<HolidayTypeComponent>;
    let service: HolidayTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [HolidayTypeComponent],
      })
        .overrideTemplate(HolidayTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HolidayTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HolidayTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new HolidayType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.holidayTypes && comp.holidayTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
