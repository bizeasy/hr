import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { ProductStoreProductComponent } from 'app/entities/product-store-product/product-store-product.component';
import { ProductStoreProductService } from 'app/entities/product-store-product/product-store-product.service';
import { ProductStoreProduct } from 'app/shared/model/product-store-product.model';

describe('Component Tests', () => {
  describe('ProductStoreProduct Management Component', () => {
    let comp: ProductStoreProductComponent;
    let fixture: ComponentFixture<ProductStoreProductComponent>;
    let service: ProductStoreProductService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductStoreProductComponent],
      })
        .overrideTemplate(ProductStoreProductComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductStoreProductComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductStoreProductService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductStoreProduct(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productStoreProducts && comp.productStoreProducts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
