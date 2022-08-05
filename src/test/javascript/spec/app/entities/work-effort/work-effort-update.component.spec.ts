import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortUpdateComponent } from 'app/entities/work-effort/work-effort-update.component';
import { WorkEffortService } from 'app/entities/work-effort/work-effort.service';
import { WorkEffort } from 'app/shared/model/work-effort.model';

describe('Component Tests', () => {
  describe('WorkEffort Management Update Component', () => {
    let comp: WorkEffortUpdateComponent;
    let fixture: ComponentFixture<WorkEffortUpdateComponent>;
    let service: WorkEffortService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WorkEffortUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkEffortUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkEffort(123);
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
        const entity = new WorkEffort();
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
