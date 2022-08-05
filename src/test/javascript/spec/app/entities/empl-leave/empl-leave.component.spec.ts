import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { EmplLeaveComponent } from 'app/entities/empl-leave/empl-leave.component';
import { EmplLeaveService } from 'app/entities/empl-leave/empl-leave.service';
import { EmplLeave } from 'app/shared/model/empl-leave.model';

describe('Component Tests', () => {
  describe('EmplLeave Management Component', () => {
    let comp: EmplLeaveComponent;
    let fixture: ComponentFixture<EmplLeaveComponent>;
    let service: EmplLeaveService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplLeaveComponent],
      })
        .overrideTemplate(EmplLeaveComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplLeaveComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplLeaveService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmplLeave(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.emplLeaves && comp.emplLeaves[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
