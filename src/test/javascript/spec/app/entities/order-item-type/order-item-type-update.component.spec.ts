import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { OrderItemTypeUpdateComponent } from 'app/entities/order-item-type/order-item-type-update.component';
import { OrderItemTypeService } from 'app/entities/order-item-type/order-item-type.service';
import { OrderItemType } from 'app/shared/model/order-item-type.model';

describe('Component Tests', () => {
  describe('OrderItemType Management Update Component', () => {
    let comp: OrderItemTypeUpdateComponent;
    let fixture: ComponentFixture<OrderItemTypeUpdateComponent>;
    let service: OrderItemTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderItemTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OrderItemTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderItemTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderItemTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderItemType(123);
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
        const entity = new OrderItemType();
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
