import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { HrTestModule } from '../../../test.module';
import { ProductStoreUserGroupComponent } from 'app/entities/product-store-user-group/product-store-user-group.component';
import { ProductStoreUserGroupService } from 'app/entities/product-store-user-group/product-store-user-group.service';
import { ProductStoreUserGroup } from 'app/shared/model/product-store-user-group.model';

describe('Component Tests', () => {
  describe('ProductStoreUserGroup Management Component', () => {
    let comp: ProductStoreUserGroupComponent;
    let fixture: ComponentFixture<ProductStoreUserGroupComponent>;
    let service: ProductStoreUserGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductStoreUserGroupComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(ProductStoreUserGroupComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductStoreUserGroupComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductStoreUserGroupService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductStoreUserGroup(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productStoreUserGroups && comp.productStoreUserGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductStoreUserGroup(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productStoreUserGroups && comp.productStoreUserGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
