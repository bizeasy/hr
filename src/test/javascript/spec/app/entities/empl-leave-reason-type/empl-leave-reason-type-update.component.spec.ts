import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplLeaveReasonTypeUpdateComponent } from 'app/entities/empl-leave-reason-type/empl-leave-reason-type-update.component';
import { EmplLeaveReasonTypeService } from 'app/entities/empl-leave-reason-type/empl-leave-reason-type.service';
import { EmplLeaveReasonType } from 'app/shared/model/empl-leave-reason-type.model';

describe('Component Tests', () => {
  describe('EmplLeaveReasonType Management Update Component', () => {
    let comp: EmplLeaveReasonTypeUpdateComponent;
    let fixture: ComponentFixture<EmplLeaveReasonTypeUpdateComponent>;
    let service: EmplLeaveReasonTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplLeaveReasonTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmplLeaveReasonTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplLeaveReasonTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplLeaveReasonTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmplLeaveReasonType(123);
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
        const entity = new EmplLeaveReasonType();
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
