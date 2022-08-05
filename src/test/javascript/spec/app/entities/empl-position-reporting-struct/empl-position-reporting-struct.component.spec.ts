import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { EmplPositionReportingStructComponent } from 'app/entities/empl-position-reporting-struct/empl-position-reporting-struct.component';
import { EmplPositionReportingStructService } from 'app/entities/empl-position-reporting-struct/empl-position-reporting-struct.service';
import { EmplPositionReportingStruct } from 'app/shared/model/empl-position-reporting-struct.model';

describe('Component Tests', () => {
  describe('EmplPositionReportingStruct Management Component', () => {
    let comp: EmplPositionReportingStructComponent;
    let fixture: ComponentFixture<EmplPositionReportingStructComponent>;
    let service: EmplPositionReportingStructService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionReportingStructComponent],
      })
        .overrideTemplate(EmplPositionReportingStructComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplPositionReportingStructComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplPositionReportingStructService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmplPositionReportingStruct(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.emplPositionReportingStructs && comp.emplPositionReportingStructs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
