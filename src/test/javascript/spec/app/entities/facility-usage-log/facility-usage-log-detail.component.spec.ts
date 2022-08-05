import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { FacilityUsageLogDetailComponent } from 'app/entities/facility-usage-log/facility-usage-log-detail.component';
import { FacilityUsageLog } from 'app/shared/model/facility-usage-log.model';

describe('Component Tests', () => {
  describe('FacilityUsageLog Management Detail Component', () => {
    let comp: FacilityUsageLogDetailComponent;
    let fixture: ComponentFixture<FacilityUsageLogDetailComponent>;
    const route = ({ data: of({ facilityUsageLog: new FacilityUsageLog(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [FacilityUsageLogDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FacilityUsageLogDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FacilityUsageLogDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load facilityUsageLog on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.facilityUsageLog).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
