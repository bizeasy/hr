import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { OrderStatusDetailComponent } from 'app/entities/order-status/order-status-detail.component';
import { OrderStatus } from 'app/shared/model/order-status.model';

describe('Component Tests', () => {
  describe('OrderStatus Management Detail Component', () => {
    let comp: OrderStatusDetailComponent;
    let fixture: ComponentFixture<OrderStatusDetailComponent>;
    const route = ({ data: of({ orderStatus: new OrderStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OrderStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load orderStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
