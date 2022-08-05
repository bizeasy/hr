import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { ProductPriceTypeComponent } from 'app/entities/product-price-type/product-price-type.component';
import { ProductPriceTypeService } from 'app/entities/product-price-type/product-price-type.service';
import { ProductPriceType } from 'app/shared/model/product-price-type.model';

describe('Component Tests', () => {
  describe('ProductPriceType Management Component', () => {
    let comp: ProductPriceTypeComponent;
    let fixture: ComponentFixture<ProductPriceTypeComponent>;
    let service: ProductPriceTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductPriceTypeComponent],
      })
        .overrideTemplate(ProductPriceTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductPriceTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductPriceTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductPriceType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productPriceTypes && comp.productPriceTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
