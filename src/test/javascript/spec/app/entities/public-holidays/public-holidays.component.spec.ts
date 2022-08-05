import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { PublicHolidaysComponent } from 'app/entities/public-holidays/public-holidays.component';
import { PublicHolidaysService } from 'app/entities/public-holidays/public-holidays.service';
import { PublicHolidays } from 'app/shared/model/public-holidays.model';

describe('Component Tests', () => {
  describe('PublicHolidays Management Component', () => {
    let comp: PublicHolidaysComponent;
    let fixture: ComponentFixture<PublicHolidaysComponent>;
    let service: PublicHolidaysService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PublicHolidaysComponent],
      })
        .overrideTemplate(PublicHolidaysComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PublicHolidaysComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PublicHolidaysService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PublicHolidays(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.publicHolidays && comp.publicHolidays[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
