import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { EmplLeaveReasonTypeComponent } from 'app/entities/empl-leave-reason-type/empl-leave-reason-type.component';
import { EmplLeaveReasonTypeService } from 'app/entities/empl-leave-reason-type/empl-leave-reason-type.service';
import { EmplLeaveReasonType } from 'app/shared/model/empl-leave-reason-type.model';

describe('Component Tests', () => {
  describe('EmplLeaveReasonType Management Component', () => {
    let comp: EmplLeaveReasonTypeComponent;
    let fixture: ComponentFixture<EmplLeaveReasonTypeComponent>;
    let service: EmplLeaveReasonTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplLeaveReasonTypeComponent],
      })
        .overrideTemplate(EmplLeaveReasonTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplLeaveReasonTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplLeaveReasonTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmplLeaveReasonType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.emplLeaveReasonTypes && comp.emplLeaveReasonTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
