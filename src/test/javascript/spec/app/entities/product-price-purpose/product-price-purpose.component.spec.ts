import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { ProductPricePurposeComponent } from 'app/entities/product-price-purpose/product-price-purpose.component';
import { ProductPricePurposeService } from 'app/entities/product-price-purpose/product-price-purpose.service';
import { ProductPricePurpose } from 'app/shared/model/product-price-purpose.model';

describe('Component Tests', () => {
  describe('ProductPricePurpose Management Component', () => {
    let comp: ProductPricePurposeComponent;
    let fixture: ComponentFixture<ProductPricePurposeComponent>;
    let service: ProductPricePurposeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductPricePurposeComponent],
      })
        .overrideTemplate(ProductPricePurposeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductPricePurposeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductPricePurposeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductPricePurpose(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productPricePurposes && comp.productPricePurposes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
