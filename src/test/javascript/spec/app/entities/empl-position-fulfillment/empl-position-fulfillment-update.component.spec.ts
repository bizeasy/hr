import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplPositionFulfillmentUpdateComponent } from 'app/entities/empl-position-fulfillment/empl-position-fulfillment-update.component';
import { EmplPositionFulfillmentService } from 'app/entities/empl-position-fulfillment/empl-position-fulfillment.service';
import { EmplPositionFulfillment } from 'app/shared/model/empl-position-fulfillment.model';

describe('Component Tests', () => {
  describe('EmplPositionFulfillment Management Update Component', () => {
    let comp: EmplPositionFulfillmentUpdateComponent;
    let fixture: ComponentFixture<EmplPositionFulfillmentUpdateComponent>;
    let service: EmplPositionFulfillmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionFulfillmentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmplPositionFulfillmentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplPositionFulfillmentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplPositionFulfillmentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmplPositionFulfillment(123);
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
        const entity = new EmplPositionFulfillment();
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
