import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { EmplLeaveTypeComponent } from 'app/entities/empl-leave-type/empl-leave-type.component';
import { EmplLeaveTypeService } from 'app/entities/empl-leave-type/empl-leave-type.service';
import { EmplLeaveType } from 'app/shared/model/empl-leave-type.model';

describe('Component Tests', () => {
  describe('EmplLeaveType Management Component', () => {
    let comp: EmplLeaveTypeComponent;
    let fixture: ComponentFixture<EmplLeaveTypeComponent>;
    let service: EmplLeaveTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplLeaveTypeComponent],
      })
        .overrideTemplate(EmplLeaveTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplLeaveTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplLeaveTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmplLeaveType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.emplLeaveTypes && comp.emplLeaveTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
