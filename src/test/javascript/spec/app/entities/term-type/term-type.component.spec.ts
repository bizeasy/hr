import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { TermTypeComponent } from 'app/entities/term-type/term-type.component';
import { TermTypeService } from 'app/entities/term-type/term-type.service';
import { TermType } from 'app/shared/model/term-type.model';

describe('Component Tests', () => {
  describe('TermType Management Component', () => {
    let comp: TermTypeComponent;
    let fixture: ComponentFixture<TermTypeComponent>;
    let service: TermTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TermTypeComponent],
      })
        .overrideTemplate(TermTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TermTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TermTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TermType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.termTypes && comp.termTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
