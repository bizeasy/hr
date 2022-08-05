import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { InventoryItemDelegateUpdateComponent } from 'app/entities/inventory-item-delegate/inventory-item-delegate-update.component';
import { InventoryItemDelegateService } from 'app/entities/inventory-item-delegate/inventory-item-delegate.service';
import { InventoryItemDelegate } from 'app/shared/model/inventory-item-delegate.model';

describe('Component Tests', () => {
  describe('InventoryItemDelegate Management Update Component', () => {
    let comp: InventoryItemDelegateUpdateComponent;
    let fixture: ComponentFixture<InventoryItemDelegateUpdateComponent>;
    let service: InventoryItemDelegateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InventoryItemDelegateUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InventoryItemDelegateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventoryItemDelegateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryItemDelegateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InventoryItemDelegate(123);
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
        const entity = new InventoryItemDelegate();
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
