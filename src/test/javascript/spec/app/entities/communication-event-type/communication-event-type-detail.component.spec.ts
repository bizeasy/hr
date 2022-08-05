import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { CommunicationEventTypeDetailComponent } from 'app/entities/communication-event-type/communication-event-type-detail.component';
import { CommunicationEventType } from 'app/shared/model/communication-event-type.model';

describe('Component Tests', () => {
  describe('CommunicationEventType Management Detail Component', () => {
    let comp: CommunicationEventTypeDetailComponent;
    let fixture: ComponentFixture<CommunicationEventTypeDetailComponent>;
    const route = ({ data: of({ communicationEventType: new CommunicationEventType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [CommunicationEventTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CommunicationEventTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommunicationEventTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load communicationEventType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.communicationEventType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
