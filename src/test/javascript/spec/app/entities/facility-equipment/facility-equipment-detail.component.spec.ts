import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { FacilityEquipmentDetailComponent } from 'app/entities/facility-equipment/facility-equipment-detail.component';
import { FacilityEquipment } from 'app/shared/model/facility-equipment.model';

describe('Component Tests', () => {
  describe('FacilityEquipment Management Detail Component', () => {
    let comp: FacilityEquipmentDetailComponent;
    let fixture: ComponentFixture<FacilityEquipmentDetailComponent>;
    const route = ({ data: of({ facilityEquipment: new FacilityEquipment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [FacilityEquipmentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FacilityEquipmentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FacilityEquipmentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load facilityEquipment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.facilityEquipment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
