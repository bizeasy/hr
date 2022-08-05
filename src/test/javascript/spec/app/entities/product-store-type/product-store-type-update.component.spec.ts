import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductStoreTypeUpdateComponent } from 'app/entities/product-store-type/product-store-type-update.component';
import { ProductStoreTypeService } from 'app/entities/product-store-type/product-store-type.service';
import { ProductStoreType } from 'app/shared/model/product-store-type.model';

describe('Component Tests', () => {
  describe('ProductStoreType Management Update Component', () => {
    let comp: ProductStoreTypeUpdateComponent;
    let fixture: ComponentFixture<ProductStoreTypeUpdateComponent>;
    let service: ProductStoreTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductStoreTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProductStoreTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductStoreTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductStoreTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductStoreType(123);
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
        const entity = new ProductStoreType();
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
