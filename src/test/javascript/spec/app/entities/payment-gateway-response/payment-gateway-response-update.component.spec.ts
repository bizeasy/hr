import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PaymentGatewayResponseUpdateComponent } from 'app/entities/payment-gateway-response/payment-gateway-response-update.component';
import { PaymentGatewayResponseService } from 'app/entities/payment-gateway-response/payment-gateway-response.service';
import { PaymentGatewayResponse } from 'app/shared/model/payment-gateway-response.model';

describe('Component Tests', () => {
  describe('PaymentGatewayResponse Management Update Component', () => {
    let comp: PaymentGatewayResponseUpdateComponent;
    let fixture: ComponentFixture<PaymentGatewayResponseUpdateComponent>;
    let service: PaymentGatewayResponseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PaymentGatewayResponseUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PaymentGatewayResponseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentGatewayResponseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentGatewayResponseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentGatewayResponse(123);
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
        const entity = new PaymentGatewayResponse();
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
