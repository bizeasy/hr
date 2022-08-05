import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { ShiftHolidaysComponent } from 'app/entities/shift-holidays/shift-holidays.component';
import { ShiftHolidaysService } from 'app/entities/shift-holidays/shift-holidays.service';
import { ShiftHolidays } from 'app/shared/model/shift-holidays.model';

describe('Component Tests', () => {
  describe('ShiftHolidays Management Component', () => {
    let comp: ShiftHolidaysComponent;
    let fixture: ComponentFixture<ShiftHolidaysComponent>;
    let service: ShiftHolidaysService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ShiftHolidaysComponent],
      })
        .overrideTemplate(ShiftHolidaysComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShiftHolidaysComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShiftHolidaysService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ShiftHolidays(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.shiftHolidays && comp.shiftHolidays[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
