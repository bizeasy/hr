import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { OrderTermTypeUpdateComponent } from 'app/entities/order-term-type/order-term-type-update.component';
import { OrderTermTypeService } from 'app/entities/order-term-type/order-term-type.service';
import { OrderTermType } from 'app/shared/model/order-term-type.model';

describe('Component Tests', () => {
  describe('OrderTermType Management Update Component', () => {
    let comp: OrderTermTypeUpdateComponent;
    let fixture: ComponentFixture<OrderTermTypeUpdateComponent>;
    let service: OrderTermTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderTermTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OrderTermTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderTermTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderTermTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderTermType(123);
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
        const entity = new OrderTermType();
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
