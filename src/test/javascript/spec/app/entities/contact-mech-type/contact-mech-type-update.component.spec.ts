import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ContactMechTypeUpdateComponent } from 'app/entities/contact-mech-type/contact-mech-type-update.component';
import { ContactMechTypeService } from 'app/entities/contact-mech-type/contact-mech-type.service';
import { ContactMechType } from 'app/shared/model/contact-mech-type.model';

describe('Component Tests', () => {
  describe('ContactMechType Management Update Component', () => {
    let comp: ContactMechTypeUpdateComponent;
    let fixture: ComponentFixture<ContactMechTypeUpdateComponent>;
    let service: ContactMechTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ContactMechTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ContactMechTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactMechTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactMechTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContactMechType(123);
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
        const entity = new ContactMechType();
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
