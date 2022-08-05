import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductFacilityDetailComponent } from 'app/entities/product-facility/product-facility-detail.component';
import { ProductFacility } from 'app/shared/model/product-facility.model';

describe('Component Tests', () => {
  describe('ProductFacility Management Detail Component', () => {
    let comp: ProductFacilityDetailComponent;
    let fixture: ComponentFixture<ProductFacilityDetailComponent>;
    const route = ({ data: of({ productFacility: new ProductFacility(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductFacilityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductFacilityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductFacilityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productFacility on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productFacility).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
