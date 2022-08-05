import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { EmploymentComponent } from 'app/entities/employment/employment.component';
import { EmploymentService } from 'app/entities/employment/employment.service';
import { Employment } from 'app/shared/model/employment.model';

describe('Component Tests', () => {
  describe('Employment Management Component', () => {
    let comp: EmploymentComponent;
    let fixture: ComponentFixture<EmploymentComponent>;
    let service: EmploymentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmploymentComponent],
      })
        .overrideTemplate(EmploymentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmploymentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmploymentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Employment(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.employments && comp.employments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
