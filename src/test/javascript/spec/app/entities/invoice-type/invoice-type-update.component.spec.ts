import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { InvoiceTypeUpdateComponent } from 'app/entities/invoice-type/invoice-type-update.component';
import { InvoiceTypeService } from 'app/entities/invoice-type/invoice-type.service';
import { InvoiceType } from 'app/shared/model/invoice-type.model';

describe('Component Tests', () => {
  describe('InvoiceType Management Update Component', () => {
    let comp: InvoiceTypeUpdateComponent;
    let fixture: ComponentFixture<InvoiceTypeUpdateComponent>;
    let service: InvoiceTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InvoiceTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InvoiceTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvoiceTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvoiceTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InvoiceType(123);
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
        const entity = new InvoiceType();
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
