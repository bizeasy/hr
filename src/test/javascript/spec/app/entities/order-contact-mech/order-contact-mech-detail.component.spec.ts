import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { OrderContactMechDetailComponent } from 'app/entities/order-contact-mech/order-contact-mech-detail.component';
import { OrderContactMech } from 'app/shared/model/order-contact-mech.model';

describe('Component Tests', () => {
  describe('OrderContactMech Management Detail Component', () => {
    let comp: OrderContactMechDetailComponent;
    let fixture: ComponentFixture<OrderContactMechDetailComponent>;
    const route = ({ data: of({ orderContactMech: new OrderContactMech(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderContactMechDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OrderContactMechDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderContactMechDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load orderContactMech on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderContactMech).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
