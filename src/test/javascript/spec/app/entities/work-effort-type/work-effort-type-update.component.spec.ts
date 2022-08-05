import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortTypeUpdateComponent } from 'app/entities/work-effort-type/work-effort-type-update.component';
import { WorkEffortTypeService } from 'app/entities/work-effort-type/work-effort-type.service';
import { WorkEffortType } from 'app/shared/model/work-effort-type.model';

describe('Component Tests', () => {
  describe('WorkEffortType Management Update Component', () => {
    let comp: WorkEffortTypeUpdateComponent;
    let fixture: ComponentFixture<WorkEffortTypeUpdateComponent>;
    let service: WorkEffortTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WorkEffortTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkEffortTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkEffortType(123);
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
        const entity = new WorkEffortType();
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
