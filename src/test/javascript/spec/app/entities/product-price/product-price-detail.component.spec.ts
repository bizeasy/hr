import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductPriceDetailComponent } from 'app/entities/product-price/product-price-detail.component';
import { ProductPrice } from 'app/shared/model/product-price.model';

describe('Component Tests', () => {
  describe('ProductPrice Management Detail Component', () => {
    let comp: ProductPriceDetailComponent;
    let fixture: ComponentFixture<ProductPriceDetailComponent>;
    const route = ({ data: of({ productPrice: new ProductPrice(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductPriceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductPriceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductPriceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productPrice on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productPrice).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
