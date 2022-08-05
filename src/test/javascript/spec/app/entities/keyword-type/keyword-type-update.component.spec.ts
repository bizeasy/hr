import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { KeywordTypeUpdateComponent } from 'app/entities/keyword-type/keyword-type-update.component';
import { KeywordTypeService } from 'app/entities/keyword-type/keyword-type.service';
import { KeywordType } from 'app/shared/model/keyword-type.model';

describe('Component Tests', () => {
  describe('KeywordType Management Update Component', () => {
    let comp: KeywordTypeUpdateComponent;
    let fixture: ComponentFixture<KeywordTypeUpdateComponent>;
    let service: KeywordTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [KeywordTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(KeywordTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KeywordTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KeywordTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new KeywordType(123);
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
        const entity = new KeywordType();
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
