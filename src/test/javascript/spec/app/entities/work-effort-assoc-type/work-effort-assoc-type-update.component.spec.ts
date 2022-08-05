import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortAssocTypeUpdateComponent } from 'app/entities/work-effort-assoc-type/work-effort-assoc-type-update.component';
import { WorkEffortAssocTypeService } from 'app/entities/work-effort-assoc-type/work-effort-assoc-type.service';
import { WorkEffortAssocType } from 'app/shared/model/work-effort-assoc-type.model';

describe('Component Tests', () => {
  describe('WorkEffortAssocType Management Update Component', () => {
    let comp: WorkEffortAssocTypeUpdateComponent;
    let fixture: ComponentFixture<WorkEffortAssocTypeUpdateComponent>;
    let service: WorkEffortAssocTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortAssocTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WorkEffortAssocTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkEffortAssocTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortAssocTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkEffortAssocType(123);
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
        const entity = new WorkEffortAssocType();
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
