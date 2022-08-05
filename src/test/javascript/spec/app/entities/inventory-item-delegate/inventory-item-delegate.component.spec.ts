import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { HrTestModule } from '../../../test.module';
import { InventoryItemDelegateComponent } from 'app/entities/inventory-item-delegate/inventory-item-delegate.component';
import { InventoryItemDelegateService } from 'app/entities/inventory-item-delegate/inventory-item-delegate.service';
import { InventoryItemDelegate } from 'app/shared/model/inventory-item-delegate.model';

describe('Component Tests', () => {
  describe('InventoryItemDelegate Management Component', () => {
    let comp: InventoryItemDelegateComponent;
    let fixture: ComponentFixture<InventoryItemDelegateComponent>;
    let service: InventoryItemDelegateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InventoryItemDelegateComponent],
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
        .overrideTemplate(InventoryItemDelegateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventoryItemDelegateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryItemDelegateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InventoryItemDelegate(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.inventoryItemDelegates && comp.inventoryItemDelegates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InventoryItemDelegate(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.inventoryItemDelegates && comp.inventoryItemDelegates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
