import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PayGradeDetailComponent } from 'app/entities/pay-grade/pay-grade-detail.component';
import { PayGrade } from 'app/shared/model/pay-grade.model';

describe('Component Tests', () => {
  describe('PayGrade Management Detail Component', () => {
    let comp: PayGradeDetailComponent;
    let fixture: ComponentFixture<PayGradeDetailComponent>;
    const route = ({ data: of({ payGrade: new PayGrade(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PayGradeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PayGradeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PayGradeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load payGrade on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.payGrade).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
