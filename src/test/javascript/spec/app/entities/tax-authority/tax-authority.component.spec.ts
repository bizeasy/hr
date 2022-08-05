import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { TaxAuthorityComponent } from 'app/entities/tax-authority/tax-authority.component';
import { TaxAuthorityService } from 'app/entities/tax-authority/tax-authority.service';
import { TaxAuthority } from 'app/shared/model/tax-authority.model';

describe('Component Tests', () => {
  describe('TaxAuthority Management Component', () => {
    let comp: TaxAuthorityComponent;
    let fixture: ComponentFixture<TaxAuthorityComponent>;
    let service: TaxAuthorityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TaxAuthorityComponent],
      })
        .overrideTemplate(TaxAuthorityComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaxAuthorityComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaxAuthorityService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TaxAuthority(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.taxAuthorities && comp.taxAuthorities[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
