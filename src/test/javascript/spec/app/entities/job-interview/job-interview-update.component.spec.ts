import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { JobInterviewUpdateComponent } from 'app/entities/job-interview/job-interview-update.component';
import { JobInterviewService } from 'app/entities/job-interview/job-interview.service';
import { JobInterview } from 'app/shared/model/job-interview.model';

describe('Component Tests', () => {
  describe('JobInterview Management Update Component', () => {
    let comp: JobInterviewUpdateComponent;
    let fixture: ComponentFixture<JobInterviewUpdateComponent>;
    let service: JobInterviewService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [JobInterviewUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JobInterviewUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobInterviewUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobInterviewService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JobInterview(123);
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
        const entity = new JobInterview();
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
