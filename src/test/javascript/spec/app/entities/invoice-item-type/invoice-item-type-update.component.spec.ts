import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { InvoiceItemTypeUpdateComponent } from 'app/entities/invoice-item-type/invoice-item-type-update.component';
import { InvoiceItemTypeService } from 'app/entities/invoice-item-type/invoice-item-type.service';
import { InvoiceItemType } from 'app/shared/model/invoice-item-type.model';

describe('Component Tests', () => {
  describe('InvoiceItemType Management Update Component', () => {
    let comp: InvoiceItemTypeUpdateComponent;
    let fixture: ComponentFixture<InvoiceItemTypeUpdateComponent>;
    let service: InvoiceItemTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InvoiceItemTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InvoiceItemTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvoiceItemTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvoiceItemTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InvoiceItemType(123);
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
        const entity = new InvoiceItemType();
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
