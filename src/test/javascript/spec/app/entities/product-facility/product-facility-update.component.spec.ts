import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductFacilityUpdateComponent } from 'app/entities/product-facility/product-facility-update.component';
import { ProductFacilityService } from 'app/entities/product-facility/product-facility.service';
import { ProductFacility } from 'app/shared/model/product-facility.model';

describe('Component Tests', () => {
  describe('ProductFacility Management Update Component', () => {
    let comp: ProductFacilityUpdateComponent;
    let fixture: ComponentFixture<ProductFacilityUpdateComponent>;
    let service: ProductFacilityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductFacilityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProductFacilityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductFacilityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductFacilityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductFacility(123);
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
        const entity = new ProductFacility();
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
