import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { HrTestModule } from '../../../test.module';
import { StatusValidChangeDetailComponent } from 'app/entities/status-valid-change/status-valid-change-detail.component';
import { StatusValidChange } from 'app/shared/model/status-valid-change.model';

describe('Component Tests', () => {
  describe('StatusValidChange Management Detail Component', () => {
    let comp: StatusValidChangeDetailComponent;
    let fixture: ComponentFixture<StatusValidChangeDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ statusValidChange: new StatusValidChange(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [StatusValidChangeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(StatusValidChangeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StatusValidChangeDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load statusValidChange on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.statusValidChange).toEqual(jasmine.objectContaining({ id: 123 }));
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
