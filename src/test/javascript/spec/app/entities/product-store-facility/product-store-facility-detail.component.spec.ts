import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductStoreFacilityDetailComponent } from 'app/entities/product-store-facility/product-store-facility-detail.component';
import { ProductStoreFacility } from 'app/shared/model/product-store-facility.model';

describe('Component Tests', () => {
  describe('ProductStoreFacility Management Detail Component', () => {
    let comp: ProductStoreFacilityDetailComponent;
    let fixture: ComponentFixture<ProductStoreFacilityDetailComponent>;
    const route = ({ data: of({ productStoreFacility: new ProductStoreFacility(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductStoreFacilityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductStoreFacilityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductStoreFacilityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productStoreFacility on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productStoreFacility).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
