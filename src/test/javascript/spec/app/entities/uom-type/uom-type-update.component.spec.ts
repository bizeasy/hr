import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { UomTypeUpdateComponent } from 'app/entities/uom-type/uom-type-update.component';
import { UomTypeService } from 'app/entities/uom-type/uom-type.service';
import { UomType } from 'app/shared/model/uom-type.model';

describe('Component Tests', () => {
  describe('UomType Management Update Component', () => {
    let comp: UomTypeUpdateComponent;
    let fixture: ComponentFixture<UomTypeUpdateComponent>;
    let service: UomTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [UomTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UomTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UomTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UomTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UomType(123);
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
        const entity = new UomType();
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
