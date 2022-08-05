import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductCategoryMemberDetailComponent } from 'app/entities/product-category-member/product-category-member-detail.component';
import { ProductCategoryMember } from 'app/shared/model/product-category-member.model';

describe('Component Tests', () => {
  describe('ProductCategoryMember Management Detail Component', () => {
    let comp: ProductCategoryMemberDetailComponent;
    let fixture: ComponentFixture<ProductCategoryMemberDetailComponent>;
    const route = ({ data: of({ productCategoryMember: new ProductCategoryMember(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductCategoryMemberDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductCategoryMemberDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductCategoryMemberDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productCategoryMember on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productCategoryMember).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
