import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { InterviewGradeComponent } from 'app/entities/interview-grade/interview-grade.component';
import { InterviewGradeService } from 'app/entities/interview-grade/interview-grade.service';
import { InterviewGrade } from 'app/shared/model/interview-grade.model';

describe('Component Tests', () => {
  describe('InterviewGrade Management Component', () => {
    let comp: InterviewGradeComponent;
    let fixture: ComponentFixture<InterviewGradeComponent>;
    let service: InterviewGradeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InterviewGradeComponent],
      })
        .overrideTemplate(InterviewGradeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InterviewGradeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InterviewGradeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InterviewGrade(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.interviewGrades && comp.interviewGrades[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
