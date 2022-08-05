import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortInventoryProducedUpdateComponent } from 'app/entities/work-effort-inventory-produced/work-effort-inventory-produced-update.component';
import { WorkEffortInventoryProducedService } from 'app/entities/work-effort-inventory-produced/work-effort-inventory-produced.service';
import { WorkEffortInventoryProduced } from 'app/shared/model/work-effort-inventory-produced.model';

describe('Component Tests', () => {
  describe('WorkEffortInventoryProduced Management Update Component', () => {
    let comp: WorkEffortInventoryProducedUpdateComponent;
    let fixture: ComponentFixture<WorkEffortInventoryProducedUpdateComponent>;
    let service: WorkEffortInventoryProducedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortInventoryProducedUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WorkEffortInventoryProducedUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkEffortInventoryProducedUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortInventoryProducedService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkEffortInventoryProduced(123);
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
        const entity = new WorkEffortInventoryProduced();
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
