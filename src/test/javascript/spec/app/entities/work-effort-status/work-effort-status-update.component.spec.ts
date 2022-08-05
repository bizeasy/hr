import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortStatusUpdateComponent } from 'app/entities/work-effort-status/work-effort-status-update.component';
import { WorkEffortStatusService } from 'app/entities/work-effort-status/work-effort-status.service';
import { WorkEffortStatus } from 'app/shared/model/work-effort-status.model';

describe('Component Tests', () => {
  describe('WorkEffortStatus Management Update Component', () => {
    let comp: WorkEffortStatusUpdateComponent;
    let fixture: ComponentFixture<WorkEffortStatusUpdateComponent>;
    let service: WorkEffortStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortStatusUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WorkEffortStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkEffortStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkEffortStatus(123);
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
        const entity = new WorkEffortStatus();
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
