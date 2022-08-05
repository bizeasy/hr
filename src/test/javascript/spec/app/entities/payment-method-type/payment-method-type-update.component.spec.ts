import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PaymentMethodTypeUpdateComponent } from 'app/entities/payment-method-type/payment-method-type-update.component';
import { PaymentMethodTypeService } from 'app/entities/payment-method-type/payment-method-type.service';
import { PaymentMethodType } from 'app/shared/model/payment-method-type.model';

describe('Component Tests', () => {
  describe('PaymentMethodType Management Update Component', () => {
    let comp: PaymentMethodTypeUpdateComponent;
    let fixture: ComponentFixture<PaymentMethodTypeUpdateComponent>;
    let service: PaymentMethodTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PaymentMethodTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PaymentMethodTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentMethodTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentMethodTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentMethodType(123);
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
        const entity = new PaymentMethodType();
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
