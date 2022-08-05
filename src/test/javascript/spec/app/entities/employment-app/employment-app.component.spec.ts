import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { EmploymentAppComponent } from 'app/entities/employment-app/employment-app.component';
import { EmploymentAppService } from 'app/entities/employment-app/employment-app.service';
import { EmploymentApp } from 'app/shared/model/employment-app.model';

describe('Component Tests', () => {
  describe('EmploymentApp Management Component', () => {
    let comp: EmploymentAppComponent;
    let fixture: ComponentFixture<EmploymentAppComponent>;
    let service: EmploymentAppService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmploymentAppComponent],
      })
        .overrideTemplate(EmploymentAppComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmploymentAppComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmploymentAppService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmploymentApp(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.employmentApps && comp.employmentApps[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
