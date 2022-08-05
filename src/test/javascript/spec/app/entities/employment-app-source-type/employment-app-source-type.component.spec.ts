import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { EmploymentAppSourceTypeComponent } from 'app/entities/employment-app-source-type/employment-app-source-type.component';
import { EmploymentAppSourceTypeService } from 'app/entities/employment-app-source-type/employment-app-source-type.service';
import { EmploymentAppSourceType } from 'app/shared/model/employment-app-source-type.model';

describe('Component Tests', () => {
  describe('EmploymentAppSourceType Management Component', () => {
    let comp: EmploymentAppSourceTypeComponent;
    let fixture: ComponentFixture<EmploymentAppSourceTypeComponent>;
    let service: EmploymentAppSourceTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmploymentAppSourceTypeComponent],
      })
        .overrideTemplate(EmploymentAppSourceTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmploymentAppSourceTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmploymentAppSourceTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmploymentAppSourceType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.employmentAppSourceTypes && comp.employmentAppSourceTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
