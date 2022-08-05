import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { SupplierProductDetailComponent } from 'app/entities/supplier-product/supplier-product-detail.component';
import { SupplierProduct } from 'app/shared/model/supplier-product.model';

describe('Component Tests', () => {
  describe('SupplierProduct Management Detail Component', () => {
    let comp: SupplierProductDetailComponent;
    let fixture: ComponentFixture<SupplierProductDetailComponent>;
    const route = ({ data: of({ supplierProduct: new SupplierProduct(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [SupplierProductDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SupplierProductDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SupplierProductDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load supplierProduct on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.supplierProduct).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
