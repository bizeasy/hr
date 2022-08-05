import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductStoreProductDetailComponent } from 'app/entities/product-store-product/product-store-product-detail.component';
import { ProductStoreProduct } from 'app/shared/model/product-store-product.model';

describe('Component Tests', () => {
  describe('ProductStoreProduct Management Detail Component', () => {
    let comp: ProductStoreProductDetailComponent;
    let fixture: ComponentFixture<ProductStoreProductDetailComponent>;
    const route = ({ data: of({ productStoreProduct: new ProductStoreProduct(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductStoreProductDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductStoreProductDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductStoreProductDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productStoreProduct on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productStoreProduct).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
