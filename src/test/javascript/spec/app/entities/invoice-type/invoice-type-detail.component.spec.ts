import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { InvoiceTypeDetailComponent } from 'app/entities/invoice-type/invoice-type-detail.component';
import { InvoiceType } from 'app/shared/model/invoice-type.model';

describe('Component Tests', () => {
  describe('InvoiceType Management Detail Component', () => {
    let comp: InvoiceTypeDetailComponent;
    let fixture: ComponentFixture<InvoiceTypeDetailComponent>;
    const route = ({ data: of({ invoiceType: new InvoiceType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InvoiceTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InvoiceTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvoiceTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load invoiceType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.invoiceType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
