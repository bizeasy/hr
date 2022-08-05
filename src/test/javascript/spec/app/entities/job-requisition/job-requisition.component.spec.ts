import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { JobRequisitionComponent } from 'app/entities/job-requisition/job-requisition.component';
import { JobRequisitionService } from 'app/entities/job-requisition/job-requisition.service';
import { JobRequisition } from 'app/shared/model/job-requisition.model';

describe('Component Tests', () => {
  describe('JobRequisition Management Component', () => {
    let comp: JobRequisitionComponent;
    let fixture: ComponentFixture<JobRequisitionComponent>;
    let service: JobRequisitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [JobRequisitionComponent],
      })
        .overrideTemplate(JobRequisitionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobRequisitionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobRequisitionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new JobRequisition(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.jobRequisitions && comp.jobRequisitions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
