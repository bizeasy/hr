import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { InterviewGradeUpdateComponent } from 'app/entities/interview-grade/interview-grade-update.component';
import { InterviewGradeService } from 'app/entities/interview-grade/interview-grade.service';
import { InterviewGrade } from 'app/shared/model/interview-grade.model';

describe('Component Tests', () => {
  describe('InterviewGrade Management Update Component', () => {
    let comp: InterviewGradeUpdateComponent;
    let fixture: ComponentFixture<InterviewGradeUpdateComponent>;
    let service: InterviewGradeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InterviewGradeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InterviewGradeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InterviewGradeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InterviewGradeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InterviewGrade(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new InterviewGrade();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
