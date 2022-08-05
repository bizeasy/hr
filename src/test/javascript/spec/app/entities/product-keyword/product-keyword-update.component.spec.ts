import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductKeywordUpdateComponent } from 'app/entities/product-keyword/product-keyword-update.component';
import { ProductKeywordService } from 'app/entities/product-keyword/product-keyword.service';
import { ProductKeyword } from 'app/shared/model/product-keyword.model';

describe('Component Tests', () => {
  describe('ProductKeyword Management Update Component', () => {
    let comp: ProductKeywordUpdateComponent;
    let fixture: ComponentFixture<ProductKeywordUpdateComponent>;
    let service: ProductKeywordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductKeywordUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProductKeywordUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductKeywordUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductKeywordService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductKeyword(123);
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
        const entity = new ProductKeyword();
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
