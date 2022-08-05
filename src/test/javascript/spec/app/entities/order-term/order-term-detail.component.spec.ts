import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { OrderTermDetailComponent } from 'app/entities/order-term/order-term-detail.component';
import { OrderTerm } from 'app/shared/model/order-term.model';

describe('Component Tests', () => {
  describe('OrderTerm Management Detail Component', () => {
    let comp: OrderTermDetailComponent;
    let fixture: ComponentFixture<OrderTermDetailComponent>;
    const route = ({ data: of({ orderTerm: new OrderTerm(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderTermDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OrderTermDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderTermDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load orderTerm on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderTerm).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
