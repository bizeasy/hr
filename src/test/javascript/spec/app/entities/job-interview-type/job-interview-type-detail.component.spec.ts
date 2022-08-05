import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { JobInterviewTypeDetailComponent } from 'app/entities/job-interview-type/job-interview-type-detail.component';
import { JobInterviewType } from 'app/shared/model/job-interview-type.model';

describe('Component Tests', () => {
  describe('JobInterviewType Management Detail Component', () => {
    let comp: JobInterviewTypeDetailComponent;
    let fixture: ComponentFixture<JobInterviewTypeDetailComponent>;
    const route = ({ data: of({ jobInterviewType: new JobInterviewType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [JobInterviewTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(JobInterviewTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobInterviewTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load jobInterviewType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jobInterviewType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
