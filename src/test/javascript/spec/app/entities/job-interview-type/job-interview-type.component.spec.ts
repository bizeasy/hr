import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { JobInterviewTypeComponent } from 'app/entities/job-interview-type/job-interview-type.component';
import { JobInterviewTypeService } from 'app/entities/job-interview-type/job-interview-type.service';
import { JobInterviewType } from 'app/shared/model/job-interview-type.model';

describe('Component Tests', () => {
  describe('JobInterviewType Management Component', () => {
    let comp: JobInterviewTypeComponent;
    let fixture: ComponentFixture<JobInterviewTypeComponent>;
    let service: JobInterviewTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [JobInterviewTypeComponent],
      })
        .overrideTemplate(JobInterviewTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobInterviewTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobInterviewTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new JobInterviewType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.jobInterviewTypes && comp.jobInterviewTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
