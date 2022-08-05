import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductStoreUserGroupUpdateComponent } from 'app/entities/product-store-user-group/product-store-user-group-update.component';
import { ProductStoreUserGroupService } from 'app/entities/product-store-user-group/product-store-user-group.service';
import { ProductStoreUserGroup } from 'app/shared/model/product-store-user-group.model';

describe('Component Tests', () => {
  describe('ProductStoreUserGroup Management Update Component', () => {
    let comp: ProductStoreUserGroupUpdateComponent;
    let fixture: ComponentFixture<ProductStoreUserGroupUpdateComponent>;
    let service: ProductStoreUserGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductStoreUserGroupUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProductStoreUserGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductStoreUserGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductStoreUserGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductStoreUserGroup(123);
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
        const entity = new ProductStoreUserGroup();
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
