import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PartyContactMechUpdateComponent } from 'app/entities/party-contact-mech/party-contact-mech-update.component';
import { PartyContactMechService } from 'app/entities/party-contact-mech/party-contact-mech.service';
import { PartyContactMech } from 'app/shared/model/party-contact-mech.model';

describe('Component Tests', () => {
  describe('PartyContactMech Management Update Component', () => {
    let comp: PartyContactMechUpdateComponent;
    let fixture: ComponentFixture<PartyContactMechUpdateComponent>;
    let service: PartyContactMechService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyContactMechUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PartyContactMechUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyContactMechUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyContactMechService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartyContactMech(123);
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
        const entity = new PartyContactMech();
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
