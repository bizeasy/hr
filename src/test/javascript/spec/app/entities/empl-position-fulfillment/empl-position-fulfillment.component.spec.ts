import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { EmplPositionFulfillmentComponent } from 'app/entities/empl-position-fulfillment/empl-position-fulfillment.component';
import { EmplPositionFulfillmentService } from 'app/entities/empl-position-fulfillment/empl-position-fulfillment.service';
import { EmplPositionFulfillment } from 'app/shared/model/empl-position-fulfillment.model';

describe('Component Tests', () => {
  describe('EmplPositionFulfillment Management Component', () => {
    let comp: EmplPositionFulfillmentComponent;
    let fixture: ComponentFixture<EmplPositionFulfillmentComponent>;
    let service: EmplPositionFulfillmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionFulfillmentComponent],
      })
        .overrideTemplate(EmplPositionFulfillmentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplPositionFulfillmentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplPositionFulfillmentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmplPositionFulfillment(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.emplPositionFulfillments && comp.emplPositionFulfillments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
