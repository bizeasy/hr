import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplPositionReportingStructDetailComponent } from 'app/entities/empl-position-reporting-struct/empl-position-reporting-struct-detail.component';
import { EmplPositionReportingStruct } from 'app/shared/model/empl-position-reporting-struct.model';

describe('Component Tests', () => {
  describe('EmplPositionReportingStruct Management Detail Component', () => {
    let comp: EmplPositionReportingStructDetailComponent;
    let fixture: ComponentFixture<EmplPositionReportingStructDetailComponent>;
    const route = ({ data: of({ emplPositionReportingStruct: new EmplPositionReportingStruct(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionReportingStructDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmplPositionReportingStructDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmplPositionReportingStructDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load emplPositionReportingStruct on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emplPositionReportingStruct).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
