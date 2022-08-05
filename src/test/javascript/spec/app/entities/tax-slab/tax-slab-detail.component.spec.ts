import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { TaxSlabDetailComponent } from 'app/entities/tax-slab/tax-slab-detail.component';
import { TaxSlab } from 'app/shared/model/tax-slab.model';

describe('Component Tests', () => {
  describe('TaxSlab Management Detail Component', () => {
    let comp: TaxSlabDetailComponent;
    let fixture: ComponentFixture<TaxSlabDetailComponent>;
    const route = ({ data: of({ taxSlab: new TaxSlab(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TaxSlabDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TaxSlabDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaxSlabDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taxSlab on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taxSlab).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
