import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { TerminationTypeUpdateComponent } from 'app/entities/termination-type/termination-type-update.component';
import { TerminationTypeService } from 'app/entities/termination-type/termination-type.service';
import { TerminationType } from 'app/shared/model/termination-type.model';

describe('Component Tests', () => {
  describe('TerminationType Management Update Component', () => {
    let comp: TerminationTypeUpdateComponent;
    let fixture: ComponentFixture<TerminationTypeUpdateComponent>;
    let service: TerminationTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [TerminationTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TerminationTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TerminationTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TerminationTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TerminationType(123);
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
        const entity = new TerminationType();
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
