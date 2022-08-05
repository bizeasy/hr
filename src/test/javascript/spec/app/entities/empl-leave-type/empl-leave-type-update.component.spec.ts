import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplLeaveTypeUpdateComponent } from 'app/entities/empl-leave-type/empl-leave-type-update.component';
import { EmplLeaveTypeService } from 'app/entities/empl-leave-type/empl-leave-type.service';
import { EmplLeaveType } from 'app/shared/model/empl-leave-type.model';

describe('Component Tests', () => {
  describe('EmplLeaveType Management Update Component', () => {
    let comp: EmplLeaveTypeUpdateComponent;
    let fixture: ComponentFixture<EmplLeaveTypeUpdateComponent>;
    let service: EmplLeaveTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplLeaveTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmplLeaveTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplLeaveTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplLeaveTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmplLeaveType(123);
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
        const entity = new EmplLeaveType();
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
