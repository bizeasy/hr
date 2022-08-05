import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { DeductionTypeComponent } from 'app/entities/deduction-type/deduction-type.component';
import { DeductionTypeService } from 'app/entities/deduction-type/deduction-type.service';
import { DeductionType } from 'app/shared/model/deduction-type.model';

describe('Component Tests', () => {
  describe('DeductionType Management Component', () => {
    let comp: DeductionTypeComponent;
    let fixture: ComponentFixture<DeductionTypeComponent>;
    let service: DeductionTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [DeductionTypeComponent],
      })
        .overrideTemplate(DeductionTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DeductionTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DeductionTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DeductionType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.deductionTypes && comp.deductionTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
