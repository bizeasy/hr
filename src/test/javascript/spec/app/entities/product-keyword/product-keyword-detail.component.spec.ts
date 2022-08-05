import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductKeywordDetailComponent } from 'app/entities/product-keyword/product-keyword-detail.component';
import { ProductKeyword } from 'app/shared/model/product-keyword.model';

describe('Component Tests', () => {
  describe('ProductKeyword Management Detail Component', () => {
    let comp: ProductKeywordDetailComponent;
    let fixture: ComponentFixture<ProductKeywordDetailComponent>;
    const route = ({ data: of({ productKeyword: new ProductKeyword(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductKeywordDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductKeywordDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductKeywordDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productKeyword on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productKeyword).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
