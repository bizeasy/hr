import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { OrderTypeDetailComponent } from 'app/entities/order-type/order-type-detail.component';
import { OrderType } from 'app/shared/model/order-type.model';

describe('Component Tests', () => {
  describe('OrderType Management Detail Component', () => {
    let comp: OrderTypeDetailComponent;
    let fixture: ComponentFixture<OrderTypeDetailComponent>;
    const route = ({ data: of({ orderType: new OrderType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OrderTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load orderType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
