import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PaymentApplicationUpdateComponent } from 'app/entities/payment-application/payment-application-update.component';
import { PaymentApplicationService } from 'app/entities/payment-application/payment-application.service';
import { PaymentApplication } from 'app/shared/model/payment-application.model';

describe('Component Tests', () => {
  describe('PaymentApplication Management Update Component', () => {
    let comp: PaymentApplicationUpdateComponent;
    let fixture: ComponentFixture<PaymentApplicationUpdateComponent>;
    let service: PaymentApplicationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PaymentApplicationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PaymentApplicationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentApplicationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentApplicationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentApplication(123);
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
        const entity = new PaymentApplication();
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
