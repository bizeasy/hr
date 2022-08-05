import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ContactMechPurposeUpdateComponent } from 'app/entities/contact-mech-purpose/contact-mech-purpose-update.component';
import { ContactMechPurposeService } from 'app/entities/contact-mech-purpose/contact-mech-purpose.service';
import { ContactMechPurpose } from 'app/shared/model/contact-mech-purpose.model';

describe('Component Tests', () => {
  describe('ContactMechPurpose Management Update Component', () => {
    let comp: ContactMechPurposeUpdateComponent;
    let fixture: ComponentFixture<ContactMechPurposeUpdateComponent>;
    let service: ContactMechPurposeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ContactMechPurposeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ContactMechPurposeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContactMechPurposeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContactMechPurposeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ContactMechPurpose(123);
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
        const entity = new ContactMechPurpose();
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
