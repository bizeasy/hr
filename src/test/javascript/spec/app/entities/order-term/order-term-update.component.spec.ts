import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { OrderTermUpdateComponent } from 'app/entities/order-term/order-term-update.component';
import { OrderTermService } from 'app/entities/order-term/order-term.service';
import { OrderTerm } from 'app/shared/model/order-term.model';

describe('Component Tests', () => {
  describe('OrderTerm Management Update Component', () => {
    let comp: OrderTermUpdateComponent;
    let fixture: ComponentFixture<OrderTermUpdateComponent>;
    let service: OrderTermService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderTermUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OrderTermUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderTermUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderTermService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrderTerm(123);
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
        const entity = new OrderTerm();
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
