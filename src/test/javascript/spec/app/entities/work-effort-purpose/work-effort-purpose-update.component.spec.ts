import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortPurposeUpdateComponent } from 'app/entities/work-effort-purpose/work-effort-purpose-update.component';
import { WorkEffortPurposeService } from 'app/entities/work-effort-purpose/work-effort-purpose.service';
import { WorkEffortPurpose } from 'app/shared/model/work-effort-purpose.model';

describe('Component Tests', () => {
  describe('WorkEffortPurpose Management Update Component', () => {
    let comp: WorkEffortPurposeUpdateComponent;
    let fixture: ComponentFixture<WorkEffortPurposeUpdateComponent>;
    let service: WorkEffortPurposeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortPurposeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WorkEffortPurposeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkEffortPurposeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortPurposeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkEffortPurpose(123);
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
        const entity = new WorkEffortPurpose();
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
