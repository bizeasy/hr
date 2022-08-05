import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { EmplPositionTypeRateComponent } from 'app/entities/empl-position-type-rate/empl-position-type-rate.component';
import { EmplPositionTypeRateService } from 'app/entities/empl-position-type-rate/empl-position-type-rate.service';
import { EmplPositionTypeRate } from 'app/shared/model/empl-position-type-rate.model';

describe('Component Tests', () => {
  describe('EmplPositionTypeRate Management Component', () => {
    let comp: EmplPositionTypeRateComponent;
    let fixture: ComponentFixture<EmplPositionTypeRateComponent>;
    let service: EmplPositionTypeRateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionTypeRateComponent],
      })
        .overrideTemplate(EmplPositionTypeRateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplPositionTypeRateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplPositionTypeRateService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmplPositionTypeRate(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.emplPositionTypeRates && comp.emplPositionTypeRates[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
