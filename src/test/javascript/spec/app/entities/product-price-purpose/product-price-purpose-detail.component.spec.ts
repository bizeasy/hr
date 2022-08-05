import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductPricePurposeDetailComponent } from 'app/entities/product-price-purpose/product-price-purpose-detail.component';
import { ProductPricePurpose } from 'app/shared/model/product-price-purpose.model';

describe('Component Tests', () => {
  describe('ProductPricePurpose Management Detail Component', () => {
    let comp: ProductPricePurposeDetailComponent;
    let fixture: ComponentFixture<ProductPricePurposeDetailComponent>;
    const route = ({ data: of({ productPricePurpose: new ProductPricePurpose(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductPricePurposeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductPricePurposeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductPricePurposeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productPricePurpose on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productPricePurpose).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
