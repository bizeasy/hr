import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { TermTypeUpdateComponent } from 'app/entities/term-type/term-type-update.component';
import { TermTypeService } from 'app/entities/term-type/term-type.service';
import { TermType } from 'app/shared/model/term-type.model';

describe('Component Tests', () => {
  describe('TermType Management Update Component', () => {
    let comp: TermTypeUpdateComponent;
    let fixture: ComponentFixture<TermTypeUpdateComponent>;
    let service: TermTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TermTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TermTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TermTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TermTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TermType(123);
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
        const entity = new TermType();
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
