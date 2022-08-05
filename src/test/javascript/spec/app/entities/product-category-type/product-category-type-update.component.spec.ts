import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductCategoryTypeUpdateComponent } from 'app/entities/product-category-type/product-category-type-update.component';
import { ProductCategoryTypeService } from 'app/entities/product-category-type/product-category-type.service';
import { ProductCategoryType } from 'app/shared/model/product-category-type.model';

describe('Component Tests', () => {
  describe('ProductCategoryType Management Update Component', () => {
    let comp: ProductCategoryTypeUpdateComponent;
    let fixture: ComponentFixture<ProductCategoryTypeUpdateComponent>;
    let service: ProductCategoryTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductCategoryTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProductCategoryTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductCategoryTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductCategoryTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductCategoryType(123);
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
        const entity = new ProductCategoryType();
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
