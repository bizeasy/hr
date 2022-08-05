import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { FacilityUsageLogComponent } from 'app/entities/facility-usage-log/facility-usage-log.component';
import { FacilityUsageLogService } from 'app/entities/facility-usage-log/facility-usage-log.service';
import { FacilityUsageLog } from 'app/shared/model/facility-usage-log.model';

describe('Component Tests', () => {
  describe('FacilityUsageLog Management Component', () => {
    let comp: FacilityUsageLogComponent;
    let fixture: ComponentFixture<FacilityUsageLogComponent>;
    let service: FacilityUsageLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [FacilityUsageLogComponent],
      })
        .overrideTemplate(FacilityUsageLogComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FacilityUsageLogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FacilityUsageLogService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FacilityUsageLog(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.facilityUsageLogs && comp.facilityUsageLogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
