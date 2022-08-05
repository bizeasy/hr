import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { CatalogueCategoryUpdateComponent } from 'app/entities/catalogue-category/catalogue-category-update.component';
import { CatalogueCategoryService } from 'app/entities/catalogue-category/catalogue-category.service';
import { CatalogueCategory } from 'app/shared/model/catalogue-category.model';

describe('Component Tests', () => {
  describe('CatalogueCategory Management Update Component', () => {
    let comp: CatalogueCategoryUpdateComponent;
    let fixture: ComponentFixture<CatalogueCategoryUpdateComponent>;
    let service: CatalogueCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [CatalogueCategoryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CatalogueCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CatalogueCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CatalogueCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CatalogueCategory(123);
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
        const entity = new CatalogueCategory();
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
