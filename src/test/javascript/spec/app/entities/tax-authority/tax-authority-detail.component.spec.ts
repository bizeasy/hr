import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { TaxAuthorityDetailComponent } from 'app/entities/tax-authority/tax-authority-detail.component';
import { TaxAuthority } from 'app/shared/model/tax-authority.model';

describe('Component Tests', () => {
  describe('TaxAuthority Management Detail Component', () => {
    let comp: TaxAuthorityDetailComponent;
    let fixture: ComponentFixture<TaxAuthorityDetailComponent>;
    const route = ({ data: of({ taxAuthority: new TaxAuthority(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TaxAuthorityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TaxAuthorityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaxAuthorityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taxAuthority on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taxAuthority).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
