import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { HrTestModule } from '../../../test.module';
import { PartyResumeDetailComponent } from 'app/entities/party-resume/party-resume-detail.component';
import { PartyResume } from 'app/shared/model/party-resume.model';

describe('Component Tests', () => {
  describe('PartyResume Management Detail Component', () => {
    let comp: PartyResumeDetailComponent;
    let fixture: ComponentFixture<PartyResumeDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ partyResume: new PartyResume(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyResumeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PartyResumeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartyResumeDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load partyResume on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partyResume).toEqual(jasmine.objectContaining({ id: 123 }));
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
