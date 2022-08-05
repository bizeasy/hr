import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { JobRequisitionUpdateComponent } from 'app/entities/job-requisition/job-requisition-update.component';
import { JobRequisitionService } from 'app/entities/job-requisition/job-requisition.service';
import { JobRequisition } from 'app/shared/model/job-requisition.model';

describe('Component Tests', () => {
  describe('JobRequisition Management Update Component', () => {
    let comp: JobRequisitionUpdateComponent;
    let fixture: ComponentFixture<JobRequisitionUpdateComponent>;
    let service: JobRequisitionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [JobRequisitionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JobRequisitionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JobRequisitionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JobRequisitionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JobRequisition(123);
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
        const entity = new JobRequisition();
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
