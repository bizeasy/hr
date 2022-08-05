import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { LocationTypeUpdateComponent } from 'app/entities/location-type/location-type-update.component';
import { LocationTypeService } from 'app/entities/location-type/location-type.service';
import { LocationType } from 'app/shared/model/location-type.model';

describe('Component Tests', () => {
  describe('LocationType Management Update Component', () => {
    let comp: LocationTypeUpdateComponent;
    let fixture: ComponentFixture<LocationTypeUpdateComponent>;
    let service: LocationTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [LocationTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LocationTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocationTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocationTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LocationType(123);
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
        const entity = new LocationType();
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
