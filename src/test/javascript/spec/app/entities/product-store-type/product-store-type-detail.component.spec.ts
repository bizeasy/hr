import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductStoreTypeDetailComponent } from 'app/entities/product-store-type/product-store-type-detail.component';
import { ProductStoreType } from 'app/shared/model/product-store-type.model';

describe('Component Tests', () => {
  describe('ProductStoreType Management Detail Component', () => {
    let comp: ProductStoreTypeDetailComponent;
    let fixture: ComponentFixture<ProductStoreTypeDetailComponent>;
    const route = ({ data: of({ productStoreType: new ProductStoreType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductStoreTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductStoreTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductStoreTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productStoreType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productStoreType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
