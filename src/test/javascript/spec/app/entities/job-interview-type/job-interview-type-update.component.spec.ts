import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { JobInterviewTypeUpdateComponent } from 'app/entities/job-interview-type/job-interview-type-update.component';
import { JobInterviewTypeService } from 'app/entities/job-interview-type/job-interview-type.service';
import { JobInterviewType } from 'app/shared/model/job-interview-type.model';

describe('Component Tests', () => {
  describe('JobInterviewType Management Update Component', () => {
    let comp: JobInterviewTypeUpdateComponent;
    let fixture: ComponentFixture<JobInterviewTypeUpdateComponent>;
    let service: JobInterviewTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [JobInterviewTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JobInterviewTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobInterviewTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobInterviewTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JobInterviewType(123);
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
        const entity = new JobInterviewType();
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
