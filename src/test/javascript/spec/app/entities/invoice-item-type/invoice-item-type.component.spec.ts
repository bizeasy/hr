import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { InvoiceItemTypeComponent } from 'app/entities/invoice-item-type/invoice-item-type.component';
import { InvoiceItemTypeService } from 'app/entities/invoice-item-type/invoice-item-type.service';
import { InvoiceItemType } from 'app/shared/model/invoice-item-type.model';

describe('Component Tests', () => {
  describe('InvoiceItemType Management Component', () => {
    let comp: InvoiceItemTypeComponent;
    let fixture: ComponentFixture<InvoiceItemTypeComponent>;
    let service: InvoiceItemTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InvoiceItemTypeComponent],
      })
        .overrideTemplate(InvoiceItemTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InvoiceItemTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvoiceItemTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new InvoiceItemType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.invoiceItemTypes && comp.invoiceItemTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
