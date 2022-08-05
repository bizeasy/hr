import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { JobInterviewDetailComponent } from 'app/entities/job-interview/job-interview-detail.component';
import { JobInterview } from 'app/shared/model/job-interview.model';

describe('Component Tests', () => {
  describe('JobInterview Management Detail Component', () => {
    let comp: JobInterviewDetailComponent;
    let fixture: ComponentFixture<JobInterviewDetailComponent>;
    const route = ({ data: of({ jobInterview: new JobInterview(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [JobInterviewDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(JobInterviewDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobInterviewDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load jobInterview on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jobInterview).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
