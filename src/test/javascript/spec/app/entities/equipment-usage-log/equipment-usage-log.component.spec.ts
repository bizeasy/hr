import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { EquipmentUsageLogComponent } from 'app/entities/equipment-usage-log/equipment-usage-log.component';
import { EquipmentUsageLogService } from 'app/entities/equipment-usage-log/equipment-usage-log.service';
import { EquipmentUsageLog } from 'app/shared/model/equipment-usage-log.model';

describe('Component Tests', () => {
  describe('EquipmentUsageLog Management Component', () => {
    let comp: EquipmentUsageLogComponent;
    let fixture: ComponentFixture<EquipmentUsageLogComponent>;
    let service: EquipmentUsageLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EquipmentUsageLogComponent],
      })
        .overrideTemplate(EquipmentUsageLogComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EquipmentUsageLogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EquipmentUsageLogService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EquipmentUsageLog(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.equipmentUsageLogs && comp.equipmentUsageLogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
