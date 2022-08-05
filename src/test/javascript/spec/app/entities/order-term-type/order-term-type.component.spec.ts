import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { OrderTermTypeComponent } from 'app/entities/order-term-type/order-term-type.component';
import { OrderTermTypeService } from 'app/entities/order-term-type/order-term-type.service';
import { OrderTermType } from 'app/shared/model/order-term-type.model';

describe('Component Tests', () => {
  describe('OrderTermType Management Component', () => {
    let comp: OrderTermTypeComponent;
    let fixture: ComponentFixture<OrderTermTypeComponent>;
    let service: OrderTermTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [OrderTermTypeComponent],
      })
        .overrideTemplate(OrderTermTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderTermTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrderTermTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OrderTermType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.orderTermTypes && comp.orderTermTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
