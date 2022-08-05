import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { TermUpdateComponent } from 'app/entities/term/term-update.component';
import { TermService } from 'app/entities/term/term.service';
import { Term } from 'app/shared/model/term.model';

describe('Component Tests', () => {
  describe('Term Management Update Component', () => {
    let comp: TermUpdateComponent;
    let fixture: ComponentFixture<TermUpdateComponent>;
    let service: TermService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TermUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TermUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TermUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TermService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Term(123);
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
        const entity = new Term();
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
