import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { TermDetailComponent } from 'app/entities/term/term-detail.component';
import { Term } from 'app/shared/model/term.model';

describe('Component Tests', () => {
  describe('Term Management Detail Component', () => {
    let comp: TermDetailComponent;
    let fixture: ComponentFixture<TermDetailComponent>;
    const route = ({ data: of({ term: new Term(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TermDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TermDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TermDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load term on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.term).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
