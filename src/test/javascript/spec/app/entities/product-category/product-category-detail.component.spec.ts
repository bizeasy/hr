import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { HrTestModule } from '../../../test.module';
import { ProductCategoryDetailComponent } from 'app/entities/product-category/product-category-detail.component';
import { ProductCategory } from 'app/shared/model/product-category.model';

describe('Component Tests', () => {
  describe('ProductCategory Management Detail Component', () => {
    let comp: ProductCategoryDetailComponent;
    let fixture: ComponentFixture<ProductCategoryDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ productCategory: new ProductCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductCategoryDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load productCategory on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
