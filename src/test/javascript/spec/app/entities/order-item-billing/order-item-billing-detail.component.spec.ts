import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { OrderItemBillingDetailComponent } from 'app/entities/order-item-billing/order-item-billing-detail.component';
import { OrderItemBilling } from 'app/shared/model/order-item-billing.model';

describe('Component Tests', () => {
  describe('OrderItemBilling Management Detail Component', () => {
    let comp: OrderItemBillingDetailComponent;
    let fixture: ComponentFixture<OrderItemBillingDetailComponent>;
    const route = ({ data: of({ orderItemBilling: new OrderItemBilling(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderItemBillingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OrderItemBillingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderItemBillingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load orderItemBilling on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderItemBilling).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
