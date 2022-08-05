import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { TaxAuthorityUpdateComponent } from 'app/entities/tax-authority/tax-authority-update.component';
import { TaxAuthorityService } from 'app/entities/tax-authority/tax-authority.service';
import { TaxAuthority } from 'app/shared/model/tax-authority.model';

describe('Component Tests', () => {
  describe('TaxAuthority Management Update Component', () => {
    let comp: TaxAuthorityUpdateComponent;
    let fixture: ComponentFixture<TaxAuthorityUpdateComponent>;
    let service: TaxAuthorityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TaxAuthorityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TaxAuthorityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaxAuthorityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaxAuthorityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaxAuthority(123);
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
        const entity = new TaxAuthority();
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
