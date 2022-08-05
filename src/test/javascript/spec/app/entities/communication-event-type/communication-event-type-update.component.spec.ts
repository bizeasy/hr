import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { CommunicationEventTypeUpdateComponent } from 'app/entities/communication-event-type/communication-event-type-update.component';
import { CommunicationEventTypeService } from 'app/entities/communication-event-type/communication-event-type.service';
import { CommunicationEventType } from 'app/shared/model/communication-event-type.model';

describe('Component Tests', () => {
  describe('CommunicationEventType Management Update Component', () => {
    let comp: CommunicationEventTypeUpdateComponent;
    let fixture: ComponentFixture<CommunicationEventTypeUpdateComponent>;
    let service: CommunicationEventTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [CommunicationEventTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CommunicationEventTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommunicationEventTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommunicationEventTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommunicationEventType(123);
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
        const entity = new CommunicationEventType();
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
