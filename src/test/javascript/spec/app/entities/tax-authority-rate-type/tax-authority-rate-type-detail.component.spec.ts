import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { TaxAuthorityRateTypeDetailComponent } from 'app/entities/tax-authority-rate-type/tax-authority-rate-type-detail.component';
import { TaxAuthorityRateType } from 'app/shared/model/tax-authority-rate-type.model';

describe('Component Tests', () => {
  describe('TaxAuthorityRateType Management Detail Component', () => {
    let comp: TaxAuthorityRateTypeDetailComponent;
    let fixture: ComponentFixture<TaxAuthorityRateTypeDetailComponent>;
    const route = ({ data: of({ taxAuthorityRateType: new TaxAuthorityRateType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TaxAuthorityRateTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TaxAuthorityRateTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaxAuthorityRateTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taxAuthorityRateType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taxAuthorityRateType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
