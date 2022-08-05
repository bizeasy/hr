import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { InventoryItemTypeComponent } from 'app/entities/inventory-item-type/inventory-item-type.component';
import { InventoryItemTypeService } from 'app/entities/inventory-item-type/inventory-item-type.service';
import { InventoryItemType } from 'app/shared/model/inventory-item-type.model';

describe('Component Tests', () => {
  describe('InventoryItemType Management Component', () => {
    let comp: InventoryItemTypeComponent;
    let fixture: ComponentFixture<InventoryItemTypeComponent>;
    let service: InventoryItemTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InventoryItemTypeComponent],
      })
        .overrideTemplate(InventoryItemTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventoryItemTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryItemTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InventoryItemType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.inventoryItemTypes && comp.inventoryItemTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
