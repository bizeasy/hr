import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductPriceTypeUpdateComponent } from 'app/entities/product-price-type/product-price-type-update.component';
import { ProductPriceTypeService } from 'app/entities/product-price-type/product-price-type.service';
import { ProductPriceType } from 'app/shared/model/product-price-type.model';

describe('Component Tests', () => {
  describe('ProductPriceType Management Update Component', () => {
    let comp: ProductPriceTypeUpdateComponent;
    let fixture: ComponentFixture<ProductPriceTypeUpdateComponent>;
    let service: ProductPriceTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductPriceTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProductPriceTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductPriceTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductPriceTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductPriceType(123);
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
        const entity = new ProductPriceType();
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
