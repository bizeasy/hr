import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { EmplPositionTypeComponent } from 'app/entities/empl-position-type/empl-position-type.component';
import { EmplPositionTypeService } from 'app/entities/empl-position-type/empl-position-type.service';
import { EmplPositionType } from 'app/shared/model/empl-position-type.model';

describe('Component Tests', () => {
  describe('EmplPositionType Management Component', () => {
    let comp: EmplPositionTypeComponent;
    let fixture: ComponentFixture<EmplPositionTypeComponent>;
    let service: EmplPositionTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionTypeComponent],
      })
        .overrideTemplate(EmplPositionTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplPositionTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplPositionTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmplPositionType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.emplPositionTypes && comp.emplPositionTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
