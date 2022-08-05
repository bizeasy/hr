import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplPositionTypeRateUpdateComponent } from 'app/entities/empl-position-type-rate/empl-position-type-rate-update.component';
import { EmplPositionTypeRateService } from 'app/entities/empl-position-type-rate/empl-position-type-rate.service';
import { EmplPositionTypeRate } from 'app/shared/model/empl-position-type-rate.model';

describe('Component Tests', () => {
  describe('EmplPositionTypeRate Management Update Component', () => {
    let comp: EmplPositionTypeRateUpdateComponent;
    let fixture: ComponentFixture<EmplPositionTypeRateUpdateComponent>;
    let service: EmplPositionTypeRateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionTypeRateUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmplPositionTypeRateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplPositionTypeRateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplPositionTypeRateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmplPositionTypeRate(123);
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
        const entity = new EmplPositionTypeRate();
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
