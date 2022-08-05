import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductStoreFacilityUpdateComponent } from 'app/entities/product-store-facility/product-store-facility-update.component';
import { ProductStoreFacilityService } from 'app/entities/product-store-facility/product-store-facility.service';
import { ProductStoreFacility } from 'app/shared/model/product-store-facility.model';

describe('Component Tests', () => {
  describe('ProductStoreFacility Management Update Component', () => {
    let comp: ProductStoreFacilityUpdateComponent;
    let fixture: ComponentFixture<ProductStoreFacilityUpdateComponent>;
    let service: ProductStoreFacilityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductStoreFacilityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProductStoreFacilityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductStoreFacilityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductStoreFacilityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductStoreFacility(123);
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
        const entity = new ProductStoreFacility();
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
