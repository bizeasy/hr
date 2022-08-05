import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { CommunicationEventComponent } from 'app/entities/communication-event/communication-event.component';
import { CommunicationEventService } from 'app/entities/communication-event/communication-event.service';
import { CommunicationEvent } from 'app/shared/model/communication-event.model';

describe('Component Tests', () => {
  describe('CommunicationEvent Management Component', () => {
    let comp: CommunicationEventComponent;
    let fixture: ComponentFixture<CommunicationEventComponent>;
    let service: CommunicationEventService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [CommunicationEventComponent],
      })
        .overrideTemplate(CommunicationEventComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommunicationEventComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommunicationEventService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommunicationEvent(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.communicationEvents && comp.communicationEvents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
