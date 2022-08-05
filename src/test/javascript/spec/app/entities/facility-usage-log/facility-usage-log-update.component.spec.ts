import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { FacilityUsageLogUpdateComponent } from 'app/entities/facility-usage-log/facility-usage-log-update.component';
import { FacilityUsageLogService } from 'app/entities/facility-usage-log/facility-usage-log.service';
import { FacilityUsageLog } from 'app/shared/model/facility-usage-log.model';

describe('Component Tests', () => {
  describe('FacilityUsageLog Management Update Component', () => {
    let comp: FacilityUsageLogUpdateComponent;
    let fixture: ComponentFixture<FacilityUsageLogUpdateComponent>;
    let service: FacilityUsageLogService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [FacilityUsageLogUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FacilityUsageLogUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FacilityUsageLogUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FacilityUsageLogService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FacilityUsageLog(123);
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
        const entity = new FacilityUsageLog();
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
