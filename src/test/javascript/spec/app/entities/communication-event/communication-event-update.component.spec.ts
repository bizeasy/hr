import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { CommunicationEventUpdateComponent } from 'app/entities/communication-event/communication-event-update.component';
import { CommunicationEventService } from 'app/entities/communication-event/communication-event.service';
import { CommunicationEvent } from 'app/shared/model/communication-event.model';

describe('Component Tests', () => {
  describe('CommunicationEvent Management Update Component', () => {
    let comp: CommunicationEventUpdateComponent;
    let fixture: ComponentFixture<CommunicationEventUpdateComponent>;
    let service: CommunicationEventService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [CommunicationEventUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CommunicationEventUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommunicationEventUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommunicationEventService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommunicationEvent(123);
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
        const entity = new CommunicationEvent();
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
