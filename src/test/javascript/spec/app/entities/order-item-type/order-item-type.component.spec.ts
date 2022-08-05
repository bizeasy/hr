import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { OrderItemTypeComponent } from 'app/entities/order-item-type/order-item-type.component';
import { OrderItemTypeService } from 'app/entities/order-item-type/order-item-type.service';
import { OrderItemType } from 'app/shared/model/order-item-type.model';

describe('Component Tests', () => {
  describe('OrderItemType Management Component', () => {
    let comp: OrderItemTypeComponent;
    let fixture: ComponentFixture<OrderItemTypeComponent>;
    let service: OrderItemTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderItemTypeComponent],
      })
        .overrideTemplate(OrderItemTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderItemTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderItemTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OrderItemType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.orderItemTypes && comp.orderItemTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
