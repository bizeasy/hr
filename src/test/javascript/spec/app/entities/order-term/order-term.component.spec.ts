import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { OrderTermComponent } from 'app/entities/order-term/order-term.component';
import { OrderTermService } from 'app/entities/order-term/order-term.service';
import { OrderTerm } from 'app/shared/model/order-term.model';

describe('Component Tests', () => {
  describe('OrderTerm Management Component', () => {
    let comp: OrderTermComponent;
    let fixture: ComponentFixture<OrderTermComponent>;
    let service: OrderTermService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderTermComponent],
      })
        .overrideTemplate(OrderTermComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderTermComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderTermService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OrderTerm(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.orderTerms && comp.orderTerms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
