import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortAssocUpdateComponent } from 'app/entities/work-effort-assoc/work-effort-assoc-update.component';
import { WorkEffortAssocService } from 'app/entities/work-effort-assoc/work-effort-assoc.service';
import { WorkEffortAssoc } from 'app/shared/model/work-effort-assoc.model';

describe('Component Tests', () => {
  describe('WorkEffortAssoc Management Update Component', () => {
    let comp: WorkEffortAssocUpdateComponent;
    let fixture: ComponentFixture<WorkEffortAssocUpdateComponent>;
    let service: WorkEffortAssocService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortAssocUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WorkEffortAssocUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WorkEffortAssocUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkEffortAssocService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new WorkEffortAssoc(123);
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
        const entity = new WorkEffortAssoc();
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
