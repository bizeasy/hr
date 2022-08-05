import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductPricePurposeUpdateComponent } from 'app/entities/product-price-purpose/product-price-purpose-update.component';
import { ProductPricePurposeService } from 'app/entities/product-price-purpose/product-price-purpose.service';
import { ProductPricePurpose } from 'app/shared/model/product-price-purpose.model';

describe('Component Tests', () => {
  describe('ProductPricePurpose Management Update Component', () => {
    let comp: ProductPricePurposeUpdateComponent;
    let fixture: ComponentFixture<ProductPricePurposeUpdateComponent>;
    let service: ProductPricePurposeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductPricePurposeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProductPricePurposeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductPricePurposeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductPricePurposeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductPricePurpose(123);
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
        const entity = new ProductPricePurpose();
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
