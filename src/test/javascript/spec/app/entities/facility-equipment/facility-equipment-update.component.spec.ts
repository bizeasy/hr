import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { FacilityEquipmentUpdateComponent } from 'app/entities/facility-equipment/facility-equipment-update.component';
import { FacilityEquipmentService } from 'app/entities/facility-equipment/facility-equipment.service';
import { FacilityEquipment } from 'app/shared/model/facility-equipment.model';

describe('Component Tests', () => {
  describe('FacilityEquipment Management Update Component', () => {
    let comp: FacilityEquipmentUpdateComponent;
    let fixture: ComponentFixture<FacilityEquipmentUpdateComponent>;
    let service: FacilityEquipmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [FacilityEquipmentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FacilityEquipmentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FacilityEquipmentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FacilityEquipmentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FacilityEquipment(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new FacilityEquipment();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
