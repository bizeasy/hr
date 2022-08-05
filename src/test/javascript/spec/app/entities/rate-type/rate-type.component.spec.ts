import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { RateTypeComponent } from 'app/entities/rate-type/rate-type.component';
import { RateTypeService } from 'app/entities/rate-type/rate-type.service';
import { RateType } from 'app/shared/model/rate-type.model';

describe('Component Tests', () => {
  describe('RateType Management Component', () => {
    let comp: RateTypeComponent;
    let fixture: ComponentFixture<RateTypeComponent>;
    let service: RateTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [RateTypeComponent],
      })
        .overrideTemplate(RateTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RateTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RateTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RateType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.rateTypes && comp.rateTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
