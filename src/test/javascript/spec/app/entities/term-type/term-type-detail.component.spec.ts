import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { TermTypeDetailComponent } from 'app/entities/term-type/term-type-detail.component';
import { TermType } from 'app/shared/model/term-type.model';

describe('Component Tests', () => {
  describe('TermType Management Detail Component', () => {
    let comp: TermTypeDetailComponent;
    let fixture: ComponentFixture<TermTypeDetailComponent>;
    const route = ({ data: of({ termType: new TermType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TermTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TermTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TermTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load termType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.termType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
