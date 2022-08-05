import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplPositionFulfillmentDetailComponent } from 'app/entities/empl-position-fulfillment/empl-position-fulfillment-detail.component';
import { EmplPositionFulfillment } from 'app/shared/model/empl-position-fulfillment.model';

describe('Component Tests', () => {
  describe('EmplPositionFulfillment Management Detail Component', () => {
    let comp: EmplPositionFulfillmentDetailComponent;
    let fixture: ComponentFixture<EmplPositionFulfillmentDetailComponent>;
    const route = ({ data: of({ emplPositionFulfillment: new EmplPositionFulfillment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionFulfillmentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmplPositionFulfillmentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmplPositionFulfillmentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load emplPositionFulfillment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emplPositionFulfillment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
