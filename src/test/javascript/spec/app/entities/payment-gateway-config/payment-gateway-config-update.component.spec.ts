import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PaymentGatewayConfigUpdateComponent } from 'app/entities/payment-gateway-config/payment-gateway-config-update.component';
import { PaymentGatewayConfigService } from 'app/entities/payment-gateway-config/payment-gateway-config.service';
import { PaymentGatewayConfig } from 'app/shared/model/payment-gateway-config.model';

describe('Component Tests', () => {
  describe('PaymentGatewayConfig Management Update Component', () => {
    let comp: PaymentGatewayConfigUpdateComponent;
    let fixture: ComponentFixture<PaymentGatewayConfigUpdateComponent>;
    let service: PaymentGatewayConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PaymentGatewayConfigUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PaymentGatewayConfigUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentGatewayConfigUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentGatewayConfigService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentGatewayConfig(123);
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
        const entity = new PaymentGatewayConfig();
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
