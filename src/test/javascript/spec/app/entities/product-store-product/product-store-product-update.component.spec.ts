import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductStoreProductUpdateComponent } from 'app/entities/product-store-product/product-store-product-update.component';
import { ProductStoreProductService } from 'app/entities/product-store-product/product-store-product.service';
import { ProductStoreProduct } from 'app/shared/model/product-store-product.model';

describe('Component Tests', () => {
  describe('ProductStoreProduct Management Update Component', () => {
    let comp: ProductStoreProductUpdateComponent;
    let fixture: ComponentFixture<ProductStoreProductUpdateComponent>;
    let service: ProductStoreProductService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductStoreProductUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProductStoreProductUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductStoreProductUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductStoreProductService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductStoreProduct(123);
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
        const entity = new ProductStoreProduct();
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
