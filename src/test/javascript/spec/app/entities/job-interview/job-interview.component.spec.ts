import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { JobInterviewComponent } from 'app/entities/job-interview/job-interview.component';
import { JobInterviewService } from 'app/entities/job-interview/job-interview.service';
import { JobInterview } from 'app/shared/model/job-interview.model';

describe('Component Tests', () => {
  describe('JobInterview Management Component', () => {
    let comp: JobInterviewComponent;
    let fixture: ComponentFixture<JobInterviewComponent>;
    let service: JobInterviewService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [JobInterviewComponent],
      })
        .overrideTemplate(JobInterviewComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobInterviewComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobInterviewService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new JobInterview(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.jobInterviews && comp.jobInterviews[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
