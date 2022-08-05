import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductCategoryTypeDetailComponent } from 'app/entities/product-category-type/product-category-type-detail.component';
import { ProductCategoryType } from 'app/shared/model/product-category-type.model';

describe('Component Tests', () => {
  describe('ProductCategoryType Management Detail Component', () => {
    let comp: ProductCategoryTypeDetailComponent;
    let fixture: ComponentFixture<ProductCategoryTypeDetailComponent>;
    const route = ({ data: of({ productCategoryType: new ProductCategoryType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductCategoryTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductCategoryTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductCategoryTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productCategoryType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productCategoryType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
