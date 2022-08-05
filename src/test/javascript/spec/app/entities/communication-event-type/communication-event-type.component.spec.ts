import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { CommunicationEventTypeComponent } from 'app/entities/communication-event-type/communication-event-type.component';
import { CommunicationEventTypeService } from 'app/entities/communication-event-type/communication-event-type.service';
import { CommunicationEventType } from 'app/shared/model/communication-event-type.model';

describe('Component Tests', () => {
  describe('CommunicationEventType Management Component', () => {
    let comp: CommunicationEventTypeComponent;
    let fixture: ComponentFixture<CommunicationEventTypeComponent>;
    let service: CommunicationEventTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [CommunicationEventTypeComponent],
      })
        .overrideTemplate(CommunicationEventTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommunicationEventTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommunicationEventTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommunicationEventType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.communicationEventTypes && comp.communicationEventTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
