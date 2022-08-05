import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { OrderTermTypeDetailComponent } from 'app/entities/order-term-type/order-term-type-detail.component';
import { OrderTermType } from 'app/shared/model/order-term-type.model';

describe('Component Tests', () => {
  describe('OrderTermType Management Detail Component', () => {
    let comp: OrderTermTypeDetailComponent;
    let fixture: ComponentFixture<OrderTermTypeDetailComponent>;
    const route = ({ data: of({ orderTermType: new OrderTermType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderTermTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OrderTermTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrderTermTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load orderTermType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.orderTermType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
