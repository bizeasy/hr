import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { InventoryItemVarianceUpdateComponent } from 'app/entities/inventory-item-variance/inventory-item-variance-update.component';
import { InventoryItemVarianceService } from 'app/entities/inventory-item-variance/inventory-item-variance.service';
import { InventoryItemVariance } from 'app/shared/model/inventory-item-variance.model';

describe('Component Tests', () => {
  describe('InventoryItemVariance Management Update Component', () => {
    let comp: InventoryItemVarianceUpdateComponent;
    let fixture: ComponentFixture<InventoryItemVarianceUpdateComponent>;
    let service: InventoryItemVarianceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InventoryItemVarianceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InventoryItemVarianceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventoryItemVarianceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryItemVarianceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InventoryItemVariance(123);
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
        const entity = new InventoryItemVariance();
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
