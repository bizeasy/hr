import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ContactMechUpdateComponent } from 'app/entities/contact-mech/contact-mech-update.component';
import { ContactMechService } from 'app/entities/contact-mech/contact-mech.service';
import { ContactMech } from 'app/shared/model/contact-mech.model';

describe('Component Tests', () => {
  describe('ContactMech Management Update Component', () => {
    let comp: ContactMechUpdateComponent;
    let fixture: ComponentFixture<ContactMechUpdateComponent>;
    let service: ContactMechService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ContactMechUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ContactMechUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactMechUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactMechService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContactMech(123);
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
        const entity = new ContactMech();
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
