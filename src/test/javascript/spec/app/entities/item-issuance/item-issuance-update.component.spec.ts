import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ItemIssuanceUpdateComponent } from 'app/entities/item-issuance/item-issuance-update.component';
import { ItemIssuanceService } from 'app/entities/item-issuance/item-issuance.service';
import { ItemIssuance } from 'app/shared/model/item-issuance.model';

describe('Component Tests', () => {
  describe('ItemIssuance Management Update Component', () => {
    let comp: ItemIssuanceUpdateComponent;
    let fixture: ComponentFixture<ItemIssuanceUpdateComponent>;
    let service: ItemIssuanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ItemIssuanceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ItemIssuanceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemIssuanceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemIssuanceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItemIssuance(123);
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
        const entity = new ItemIssuance();
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
