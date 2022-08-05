import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { TaxSlabUpdateComponent } from 'app/entities/tax-slab/tax-slab-update.component';
import { TaxSlabService } from 'app/entities/tax-slab/tax-slab.service';
import { TaxSlab } from 'app/shared/model/tax-slab.model';

describe('Component Tests', () => {
  describe('TaxSlab Management Update Component', () => {
    let comp: TaxSlabUpdateComponent;
    let fixture: ComponentFixture<TaxSlabUpdateComponent>;
    let service: TaxSlabService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TaxSlabUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TaxSlabUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaxSlabUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaxSlabService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaxSlab(123);
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
        const entity = new TaxSlab();
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
