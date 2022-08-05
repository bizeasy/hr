import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { HrTestModule } from '../../../test.module';
import { CommunicationEventDetailComponent } from 'app/entities/communication-event/communication-event-detail.component';
import { CommunicationEvent } from 'app/shared/model/communication-event.model';

describe('Component Tests', () => {
  describe('CommunicationEvent Management Detail Component', () => {
    let comp: CommunicationEventDetailComponent;
    let fixture: ComponentFixture<CommunicationEventDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ communicationEvent: new CommunicationEvent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [CommunicationEventDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CommunicationEventDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommunicationEventDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load communicationEvent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.communicationEvent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
