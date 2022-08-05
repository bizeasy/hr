import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { OrderContactMechUpdateComponent } from 'app/entities/order-contact-mech/order-contact-mech-update.component';
import { OrderContactMechService } from 'app/entities/order-contact-mech/order-contact-mech.service';
import { OrderContactMech } from 'app/shared/model/order-contact-mech.model';

describe('Component Tests', () => {
  describe('OrderContactMech Management Update Component', () => {
    let comp: OrderContactMechUpdateComponent;
    let fixture: ComponentFixture<OrderContactMechUpdateComponent>;
    let service: OrderContactMechService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderContactMechUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OrderContactMechUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderContactMechUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderContactMechService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderContactMech(123);
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
        const entity = new OrderContactMech();
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
