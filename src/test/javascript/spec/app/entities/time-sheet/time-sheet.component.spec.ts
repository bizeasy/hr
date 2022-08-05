import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { TimeSheetComponent } from 'app/entities/time-sheet/time-sheet.component';
import { TimeSheetService } from 'app/entities/time-sheet/time-sheet.service';
import { TimeSheet } from 'app/shared/model/time-sheet.model';

describe('Component Tests', () => {
  describe('TimeSheet Management Component', () => {
    let comp: TimeSheetComponent;
    let fixture: ComponentFixture<TimeSheetComponent>;
    let service: TimeSheetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TimeSheetComponent],
      })
        .overrideTemplate(TimeSheetComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TimeSheetComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TimeSheetService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TimeSheet(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.timeSheets && comp.timeSheets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
