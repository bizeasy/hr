import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { FacilityContactMechUpdateComponent } from 'app/entities/facility-contact-mech/facility-contact-mech-update.component';
import { FacilityContactMechService } from 'app/entities/facility-contact-mech/facility-contact-mech.service';
import { FacilityContactMech } from 'app/shared/model/facility-contact-mech.model';

describe('Component Tests', () => {
  describe('FacilityContactMech Management Update Component', () => {
    let comp: FacilityContactMechUpdateComponent;
    let fixture: ComponentFixture<FacilityContactMechUpdateComponent>;
    let service: FacilityContactMechService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [FacilityContactMechUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FacilityContactMechUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FacilityContactMechUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FacilityContactMechService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FacilityContactMech(123);
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
        const entity = new FacilityContactMech();
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
