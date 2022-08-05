import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { CatalogueCategoryTypeUpdateComponent } from 'app/entities/catalogue-category-type/catalogue-category-type-update.component';
import { CatalogueCategoryTypeService } from 'app/entities/catalogue-category-type/catalogue-category-type.service';
import { CatalogueCategoryType } from 'app/shared/model/catalogue-category-type.model';

describe('Component Tests', () => {
  describe('CatalogueCategoryType Management Update Component', () => {
    let comp: CatalogueCategoryTypeUpdateComponent;
    let fixture: ComponentFixture<CatalogueCategoryTypeUpdateComponent>;
    let service: CatalogueCategoryTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [CatalogueCategoryTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatalogueCategoryTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatalogueCategoryTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatalogueCategoryTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatalogueCategoryType(123);
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
        const entity = new CatalogueCategoryType();
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
