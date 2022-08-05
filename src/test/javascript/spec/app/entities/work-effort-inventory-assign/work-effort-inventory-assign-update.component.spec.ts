import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortInventoryAssignUpdateComponent } from 'app/entities/work-effort-inventory-assign/work-effort-inventory-assign-update.component';
import { WorkEffortInventoryAssignService } from 'app/entities/work-effort-inventory-assign/work-effort-inventory-assign.service';
import { WorkEffortInventoryAssign } from 'app/shared/model/work-effort-inventory-assign.model';

describe('Component Tests', () => {
  describe('WorkEffortInventoryAssign Management Update Component', () => {
    let comp: WorkEffortInventoryAssignUpdateComponent;
    let fixture: ComponentFixture<WorkEffortInventoryAssignUpdateComponent>;
    let service: WorkEffortInventoryAssignService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortInventoryAssignUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WorkEffortInventoryAssignUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkEffortInventoryAssignUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortInventoryAssignService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkEffortInventoryAssign(123);
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
        const entity = new WorkEffortInventoryAssign();
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
