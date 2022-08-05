import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { PayGradeComponent } from 'app/entities/pay-grade/pay-grade.component';
import { PayGradeService } from 'app/entities/pay-grade/pay-grade.service';
import { PayGrade } from 'app/shared/model/pay-grade.model';

describe('Component Tests', () => {
  describe('PayGrade Management Component', () => {
    let comp: PayGradeComponent;
    let fixture: ComponentFixture<PayGradeComponent>;
    let service: PayGradeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PayGradeComponent],
      })
        .overrideTemplate(PayGradeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PayGradeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PayGradeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PayGrade(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.payGrades && comp.payGrades[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
