import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EquipmentUsageLogDetailComponent } from 'app/entities/equipment-usage-log/equipment-usage-log-detail.component';
import { EquipmentUsageLog } from 'app/shared/model/equipment-usage-log.model';

describe('Component Tests', () => {
  describe('EquipmentUsageLog Management Detail Component', () => {
    let comp: EquipmentUsageLogDetailComponent;
    let fixture: ComponentFixture<EquipmentUsageLogDetailComponent>;
    const route = ({ data: of({ equipmentUsageLog: new EquipmentUsageLog(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EquipmentUsageLogDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EquipmentUsageLogDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EquipmentUsageLogDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load equipmentUsageLog on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.equipmentUsageLog).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
