import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { RateTypeUpdateComponent } from 'app/entities/rate-type/rate-type-update.component';
import { RateTypeService } from 'app/entities/rate-type/rate-type.service';
import { RateType } from 'app/shared/model/rate-type.model';

describe('Component Tests', () => {
  describe('RateType Management Update Component', () => {
    let comp: RateTypeUpdateComponent;
    let fixture: ComponentFixture<RateTypeUpdateComponent>;
    let service: RateTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [RateTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RateTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RateTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RateTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RateType(123);
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
        const entity = new RateType();
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
