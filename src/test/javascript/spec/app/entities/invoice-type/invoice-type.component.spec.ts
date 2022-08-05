import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { InvoiceTypeComponent } from 'app/entities/invoice-type/invoice-type.component';
import { InvoiceTypeService } from 'app/entities/invoice-type/invoice-type.service';
import { InvoiceType } from 'app/shared/model/invoice-type.model';

describe('Component Tests', () => {
  describe('InvoiceType Management Component', () => {
    let comp: InvoiceTypeComponent;
    let fixture: ComponentFixture<InvoiceTypeComponent>;
    let service: InvoiceTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InvoiceTypeComponent],
      })
        .overrideTemplate(InvoiceTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvoiceTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvoiceTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InvoiceType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.invoiceTypes && comp.invoiceTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
