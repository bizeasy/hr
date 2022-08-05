import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { ProductStoreTypeComponent } from 'app/entities/product-store-type/product-store-type.component';
import { ProductStoreTypeService } from 'app/entities/product-store-type/product-store-type.service';
import { ProductStoreType } from 'app/shared/model/product-store-type.model';

describe('Component Tests', () => {
  describe('ProductStoreType Management Component', () => {
    let comp: ProductStoreTypeComponent;
    let fixture: ComponentFixture<ProductStoreTypeComponent>;
    let service: ProductStoreTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductStoreTypeComponent],
      })
        .overrideTemplate(ProductStoreTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductStoreTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductStoreTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductStoreType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productStoreTypes && comp.productStoreTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
