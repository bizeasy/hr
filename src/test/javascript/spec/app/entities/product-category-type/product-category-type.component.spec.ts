import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { ProductCategoryTypeComponent } from 'app/entities/product-category-type/product-category-type.component';
import { ProductCategoryTypeService } from 'app/entities/product-category-type/product-category-type.service';
import { ProductCategoryType } from 'app/shared/model/product-category-type.model';

describe('Component Tests', () => {
  describe('ProductCategoryType Management Component', () => {
    let comp: ProductCategoryTypeComponent;
    let fixture: ComponentFixture<ProductCategoryTypeComponent>;
    let service: ProductCategoryTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductCategoryTypeComponent],
      })
        .overrideTemplate(ProductCategoryTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductCategoryTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductCategoryTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductCategoryType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productCategoryTypes && comp.productCategoryTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
