import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { TermComponent } from 'app/entities/term/term.component';
import { TermService } from 'app/entities/term/term.service';
import { Term } from 'app/shared/model/term.model';

describe('Component Tests', () => {
  describe('Term Management Component', () => {
    let comp: TermComponent;
    let fixture: ComponentFixture<TermComponent>;
    let service: TermService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TermComponent],
      })
        .overrideTemplate(TermComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TermComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TermService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Term(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.terms && comp.terms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
