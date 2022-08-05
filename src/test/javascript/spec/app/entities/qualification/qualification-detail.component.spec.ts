import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { QualificationDetailComponent } from 'app/entities/qualification/qualification-detail.component';
import { Qualification } from 'app/shared/model/qualification.model';

describe('Component Tests', () => {
  describe('Qualification Management Detail Component', () => {
    let comp: QualificationDetailComponent;
    let fixture: ComponentFixture<QualificationDetailComponent>;
    const route = ({ data: of({ qualification: new Qualification(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [QualificationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QualificationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QualificationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load qualification on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.qualification).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
