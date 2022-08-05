import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { FacilityEquipmentComponent } from 'app/entities/facility-equipment/facility-equipment.component';
import { FacilityEquipmentService } from 'app/entities/facility-equipment/facility-equipment.service';
import { FacilityEquipment } from 'app/shared/model/facility-equipment.model';

describe('Component Tests', () => {
  describe('FacilityEquipment Management Component', () => {
    let comp: FacilityEquipmentComponent;
    let fixture: ComponentFixture<FacilityEquipmentComponent>;
    let service: FacilityEquipmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [FacilityEquipmentComponent],
      })
        .overrideTemplate(FacilityEquipmentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FacilityEquipmentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FacilityEquipmentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FacilityEquipment(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.facilityEquipments && comp.facilityEquipments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
