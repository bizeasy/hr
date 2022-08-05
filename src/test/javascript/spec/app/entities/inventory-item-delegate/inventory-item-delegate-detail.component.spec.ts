import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { InventoryItemDelegateDetailComponent } from 'app/entities/inventory-item-delegate/inventory-item-delegate-detail.component';
import { InventoryItemDelegate } from 'app/shared/model/inventory-item-delegate.model';

describe('Component Tests', () => {
  describe('InventoryItemDelegate Management Detail Component', () => {
    let comp: InventoryItemDelegateDetailComponent;
    let fixture: ComponentFixture<InventoryItemDelegateDetailComponent>;
    const route = ({ data: of({ inventoryItemDelegate: new InventoryItemDelegate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InventoryItemDelegateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InventoryItemDelegateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventoryItemDelegateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load inventoryItemDelegate on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inventoryItemDelegate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
