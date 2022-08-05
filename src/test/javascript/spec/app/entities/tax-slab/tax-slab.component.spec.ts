import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { TaxSlabComponent } from 'app/entities/tax-slab/tax-slab.component';
import { TaxSlabService } from 'app/entities/tax-slab/tax-slab.service';
import { TaxSlab } from 'app/shared/model/tax-slab.model';

describe('Component Tests', () => {
  describe('TaxSlab Management Component', () => {
    let comp: TaxSlabComponent;
    let fixture: ComponentFixture<TaxSlabComponent>;
    let service: TaxSlabService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TaxSlabComponent],
      })
        .overrideTemplate(TaxSlabComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaxSlabComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaxSlabService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TaxSlab(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.taxSlabs && comp.taxSlabs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
