import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { InventoryItemVarianceDetailComponent } from 'app/entities/inventory-item-variance/inventory-item-variance-detail.component';
import { InventoryItemVariance } from 'app/shared/model/inventory-item-variance.model';

describe('Component Tests', () => {
  describe('InventoryItemVariance Management Detail Component', () => {
    let comp: InventoryItemVarianceDetailComponent;
    let fixture: ComponentFixture<InventoryItemVarianceDetailComponent>;
    const route = ({ data: of({ inventoryItemVariance: new InventoryItemVariance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InventoryItemVarianceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InventoryItemVarianceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InventoryItemVarianceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load inventoryItemVariance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.inventoryItemVariance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
