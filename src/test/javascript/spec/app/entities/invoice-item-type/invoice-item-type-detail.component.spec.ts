import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { InvoiceItemTypeDetailComponent } from 'app/entities/invoice-item-type/invoice-item-type-detail.component';
import { InvoiceItemType } from 'app/shared/model/invoice-item-type.model';

describe('Component Tests', () => {
  describe('InvoiceItemType Management Detail Component', () => {
    let comp: InvoiceItemTypeDetailComponent;
    let fixture: ComponentFixture<InvoiceItemTypeDetailComponent>;
    const route = ({ data: of({ invoiceItemType: new InvoiceItemType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InvoiceItemTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InvoiceItemTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvoiceItemTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load invoiceItemType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.invoiceItemType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
