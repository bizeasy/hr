import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { InterviewGradeDetailComponent } from 'app/entities/interview-grade/interview-grade-detail.component';
import { InterviewGrade } from 'app/shared/model/interview-grade.model';

describe('Component Tests', () => {
  describe('InterviewGrade Management Detail Component', () => {
    let comp: InterviewGradeDetailComponent;
    let fixture: ComponentFixture<InterviewGradeDetailComponent>;
    const route = ({ data: of({ interviewGrade: new InterviewGrade(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InterviewGradeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InterviewGradeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InterviewGradeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load interviewGrade on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.interviewGrade).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
