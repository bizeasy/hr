import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { PeriodTypeComponent } from 'app/entities/period-type/period-type.component';
import { PeriodTypeService } from 'app/entities/period-type/period-type.service';
import { PeriodType } from 'app/shared/model/period-type.model';

describe('Component Tests', () => {
  describe('PeriodType Management Component', () => {
    let comp: PeriodTypeComponent;
    let fixture: ComponentFixture<PeriodTypeComponent>;
    let service: PeriodTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PeriodTypeComponent],
      })
        .overrideTemplate(PeriodTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PeriodTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PeriodTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PeriodType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.periodTypes && comp.periodTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
