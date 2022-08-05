import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductPriceTypeDetailComponent } from 'app/entities/product-price-type/product-price-type-detail.component';
import { ProductPriceType } from 'app/shared/model/product-price-type.model';

describe('Component Tests', () => {
  describe('ProductPriceType Management Detail Component', () => {
    let comp: ProductPriceTypeDetailComponent;
    let fixture: ComponentFixture<ProductPriceTypeDetailComponent>;
    const route = ({ data: of({ productPriceType: new ProductPriceType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductPriceTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductPriceTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductPriceTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productPriceType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productPriceType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
