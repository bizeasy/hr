import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { JobRequisitionDetailComponent } from 'app/entities/job-requisition/job-requisition-detail.component';
import { JobRequisition } from 'app/shared/model/job-requisition.model';

describe('Component Tests', () => {
  describe('JobRequisition Management Detail Component', () => {
    let comp: JobRequisitionDetailComponent;
    let fixture: ComponentFixture<JobRequisitionDetailComponent>;
    const route = ({ data: of({ jobRequisition: new JobRequisition(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [JobRequisitionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(JobRequisitionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobRequisitionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load jobRequisition on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jobRequisition).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
