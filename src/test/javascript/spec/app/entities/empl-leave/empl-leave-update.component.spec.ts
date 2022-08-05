import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplLeaveUpdateComponent } from 'app/entities/empl-leave/empl-leave-update.component';
import { EmplLeaveService } from 'app/entities/empl-leave/empl-leave.service';
import { EmplLeave } from 'app/shared/model/empl-leave.model';

describe('Component Tests', () => {
  describe('EmplLeave Management Update Component', () => {
    let comp: EmplLeaveUpdateComponent;
    let fixture: ComponentFixture<EmplLeaveUpdateComponent>;
    let service: EmplLeaveService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplLeaveUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmplLeaveUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplLeaveUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplLeaveService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmplLeave(123);
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
        const entity = new EmplLeave();
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
