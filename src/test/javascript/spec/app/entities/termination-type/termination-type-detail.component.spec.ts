import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { TerminationTypeDetailComponent } from 'app/entities/termination-type/termination-type-detail.component';
import { TerminationType } from 'app/shared/model/termination-type.model';

describe('Component Tests', () => {
  describe('TerminationType Management Detail Component', () => {
    let comp: TerminationTypeDetailComponent;
    let fixture: ComponentFixture<TerminationTypeDetailComponent>;
    const route = ({ data: of({ terminationType: new TerminationType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TerminationTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TerminationTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TerminationTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load terminationType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.terminationType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
