import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { InventoryItemTypeDetailComponent } from 'app/entities/inventory-item-type/inventory-item-type-detail.component';
import { InventoryItemType } from 'app/shared/model/inventory-item-type.model';

describe('Component Tests', () => {
  describe('InventoryItemType Management Detail Component', () => {
    let comp: InventoryItemTypeDetailComponent;
    let fixture: ComponentFixture<InventoryItemTypeDetailComponent>;
    const route = ({ data: of({ inventoryItemType: new InventoryItemType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InventoryItemTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InventoryItemTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventoryItemTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load inventoryItemType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inventoryItemType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
