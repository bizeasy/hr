import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { SupplierProductUpdateComponent } from 'app/entities/supplier-product/supplier-product-update.component';
import { SupplierProductService } from 'app/entities/supplier-product/supplier-product.service';
import { SupplierProduct } from 'app/shared/model/supplier-product.model';

describe('Component Tests', () => {
  describe('SupplierProduct Management Update Component', () => {
    let comp: SupplierProductUpdateComponent;
    let fixture: ComponentFixture<SupplierProductUpdateComponent>;
    let service: SupplierProductService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [SupplierProductUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SupplierProductUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupplierProductUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupplierProductService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SupplierProduct(123);
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
        const entity = new SupplierProduct();
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
