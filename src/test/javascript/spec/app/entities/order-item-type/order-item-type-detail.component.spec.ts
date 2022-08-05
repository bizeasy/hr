import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { OrderItemTypeDetailComponent } from 'app/entities/order-item-type/order-item-type-detail.component';
import { OrderItemType } from 'app/shared/model/order-item-type.model';

describe('Component Tests', () => {
  describe('OrderItemType Management Detail Component', () => {
    let comp: OrderItemTypeDetailComponent;
    let fixture: ComponentFixture<OrderItemTypeDetailComponent>;
    const route = ({ data: of({ orderItemType: new OrderItemType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderItemTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OrderItemTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderItemTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load orderItemType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderItemType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
