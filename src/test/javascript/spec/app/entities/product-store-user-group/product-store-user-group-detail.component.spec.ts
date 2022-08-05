import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductStoreUserGroupDetailComponent } from 'app/entities/product-store-user-group/product-store-user-group-detail.component';
import { ProductStoreUserGroup } from 'app/shared/model/product-store-user-group.model';

describe('Component Tests', () => {
  describe('ProductStoreUserGroup Management Detail Component', () => {
    let comp: ProductStoreUserGroupDetailComponent;
    let fixture: ComponentFixture<ProductStoreUserGroupDetailComponent>;
    const route = ({ data: of({ productStoreUserGroup: new ProductStoreUserGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductStoreUserGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductStoreUserGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductStoreUserGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productStoreUserGroup on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productStoreUserGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
