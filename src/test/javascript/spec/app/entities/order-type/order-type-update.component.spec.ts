import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { OrderTypeUpdateComponent } from 'app/entities/order-type/order-type-update.component';
import { OrderTypeService } from 'app/entities/order-type/order-type.service';
import { OrderType } from 'app/shared/model/order-type.model';

describe('Component Tests', () => {
  describe('OrderType Management Update Component', () => {
    let comp: OrderTypeUpdateComponent;
    let fixture: ComponentFixture<OrderTypeUpdateComponent>;
    let service: OrderTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OrderTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderType(123);
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
        const entity = new OrderType();
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
