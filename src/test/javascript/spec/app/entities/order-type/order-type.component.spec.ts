import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { OrderTypeComponent } from 'app/entities/order-type/order-type.component';
import { OrderTypeService } from 'app/entities/order-type/order-type.service';
import { OrderType } from 'app/shared/model/order-type.model';

describe('Component Tests', () => {
  describe('OrderType Management Component', () => {
    let comp: OrderTypeComponent;
    let fixture: ComponentFixture<OrderTypeComponent>;
    let service: OrderTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderTypeComponent],
      })
        .overrideTemplate(OrderTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OrderType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.orderTypes && comp.orderTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
