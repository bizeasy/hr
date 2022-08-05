import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { TaxAuthorityRateTypeUpdateComponent } from 'app/entities/tax-authority-rate-type/tax-authority-rate-type-update.component';
import { TaxAuthorityRateTypeService } from 'app/entities/tax-authority-rate-type/tax-authority-rate-type.service';
import { TaxAuthorityRateType } from 'app/shared/model/tax-authority-rate-type.model';

describe('Component Tests', () => {
  describe('TaxAuthorityRateType Management Update Component', () => {
    let comp: TaxAuthorityRateTypeUpdateComponent;
    let fixture: ComponentFixture<TaxAuthorityRateTypeUpdateComponent>;
    let service: TaxAuthorityRateTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TaxAuthorityRateTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TaxAuthorityRateTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaxAuthorityRateTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaxAuthorityRateTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaxAuthorityRateType(123);
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
        const entity = new TaxAuthorityRateType();
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
