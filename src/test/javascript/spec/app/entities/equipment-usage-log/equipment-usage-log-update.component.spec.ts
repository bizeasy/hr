import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EquipmentUsageLogUpdateComponent } from 'app/entities/equipment-usage-log/equipment-usage-log-update.component';
import { EquipmentUsageLogService } from 'app/entities/equipment-usage-log/equipment-usage-log.service';
import { EquipmentUsageLog } from 'app/shared/model/equipment-usage-log.model';

describe('Component Tests', () => {
  describe('EquipmentUsageLog Management Update Component', () => {
    let comp: EquipmentUsageLogUpdateComponent;
    let fixture: ComponentFixture<EquipmentUsageLogUpdateComponent>;
    let service: EquipmentUsageLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EquipmentUsageLogUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EquipmentUsageLogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EquipmentUsageLogUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EquipmentUsageLogService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EquipmentUsageLog(123);
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
        const entity = new EquipmentUsageLog();
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
