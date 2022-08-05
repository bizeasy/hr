import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { InventoryTransferDetailComponent } from 'app/entities/inventory-transfer/inventory-transfer-detail.component';
import { InventoryTransfer } from 'app/shared/model/inventory-transfer.model';

describe('Component Tests', () => {
  describe('InventoryTransfer Management Detail Component', () => {
    let comp: InventoryTransferDetailComponent;
    let fixture: ComponentFixture<InventoryTransferDetailComponent>;
    const route = ({ data: of({ inventoryTransfer: new InventoryTransfer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InventoryTransferDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InventoryTransferDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventoryTransferDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load inventoryTransfer on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inventoryTransfer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
