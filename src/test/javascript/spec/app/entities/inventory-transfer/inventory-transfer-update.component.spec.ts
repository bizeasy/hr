import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { InventoryTransferUpdateComponent } from 'app/entities/inventory-transfer/inventory-transfer-update.component';
import { InventoryTransferService } from 'app/entities/inventory-transfer/inventory-transfer.service';
import { InventoryTransfer } from 'app/shared/model/inventory-transfer.model';

describe('Component Tests', () => {
  describe('InventoryTransfer Management Update Component', () => {
    let comp: InventoryTransferUpdateComponent;
    let fixture: ComponentFixture<InventoryTransferUpdateComponent>;
    let service: InventoryTransferService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [InventoryTransferUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InventoryTransferUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InventoryTransferUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InventoryTransferService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InventoryTransfer(123);
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
        const entity = new InventoryTransfer();
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
