import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplPositionTypeUpdateComponent } from 'app/entities/empl-position-type/empl-position-type-update.component';
import { EmplPositionTypeService } from 'app/entities/empl-position-type/empl-position-type.service';
import { EmplPositionType } from 'app/shared/model/empl-position-type.model';

describe('Component Tests', () => {
  describe('EmplPositionType Management Update Component', () => {
    let comp: EmplPositionTypeUpdateComponent;
    let fixture: ComponentFixture<EmplPositionTypeUpdateComponent>;
    let service: EmplPositionTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmplPositionTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplPositionTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplPositionTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmplPositionType(123);
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
        const entity = new EmplPositionType();
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
