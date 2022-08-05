import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { OrderItemBillingUpdateComponent } from 'app/entities/order-item-billing/order-item-billing-update.component';
import { OrderItemBillingService } from 'app/entities/order-item-billing/order-item-billing.service';
import { OrderItemBilling } from 'app/shared/model/order-item-billing.model';

describe('Component Tests', () => {
  describe('OrderItemBilling Management Update Component', () => {
    let comp: OrderItemBillingUpdateComponent;
    let fixture: ComponentFixture<OrderItemBillingUpdateComponent>;
    let service: OrderItemBillingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderItemBillingUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OrderItemBillingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderItemBillingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderItemBillingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderItemBilling(123);
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
        const entity = new OrderItemBilling();
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
