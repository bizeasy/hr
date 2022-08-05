import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ProductCategoryMemberUpdateComponent } from 'app/entities/product-category-member/product-category-member-update.component';
import { ProductCategoryMemberService } from 'app/entities/product-category-member/product-category-member.service';
import { ProductCategoryMember } from 'app/shared/model/product-category-member.model';

describe('Component Tests', () => {
  describe('ProductCategoryMember Management Update Component', () => {
    let comp: ProductCategoryMemberUpdateComponent;
    let fixture: ComponentFixture<ProductCategoryMemberUpdateComponent>;
    let service: ProductCategoryMemberService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ProductCategoryMemberUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProductCategoryMemberUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductCategoryMemberUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductCategoryMemberService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductCategoryMember(123);
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
        const entity = new ProductCategoryMember();
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
