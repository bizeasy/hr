import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { UomUpdateComponent } from 'app/entities/uom/uom-update.component';
import { UomService } from 'app/entities/uom/uom.service';
import { Uom } from 'app/shared/model/uom.model';

describe('Component Tests', () => {
  describe('Uom Management Update Component', () => {
    let comp: UomUpdateComponent;
    let fixture: ComponentFixture<UomUpdateComponent>;
    let service: UomService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [UomUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UomUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UomUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UomService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Uom(123);
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
        const entity = new Uom();
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
