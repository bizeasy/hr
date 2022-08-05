import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { TaxAuthorityRateTypeComponent } from 'app/entities/tax-authority-rate-type/tax-authority-rate-type.component';
import { TaxAuthorityRateTypeService } from 'app/entities/tax-authority-rate-type/tax-authority-rate-type.service';
import { TaxAuthorityRateType } from 'app/shared/model/tax-authority-rate-type.model';

describe('Component Tests', () => {
  describe('TaxAuthorityRateType Management Component', () => {
    let comp: TaxAuthorityRateTypeComponent;
    let fixture: ComponentFixture<TaxAuthorityRateTypeComponent>;
    let service: TaxAuthorityRateTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TaxAuthorityRateTypeComponent],
      })
        .overrideTemplate(TaxAuthorityRateTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaxAuthorityRateTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaxAuthorityRateTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TaxAuthorityRateType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.taxAuthorityRateTypes && comp.taxAuthorityRateTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
