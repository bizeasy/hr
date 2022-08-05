import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { FacilityPartyUpdateComponent } from 'app/entities/facility-party/facility-party-update.component';
import { FacilityPartyService } from 'app/entities/facility-party/facility-party.service';
import { FacilityParty } from 'app/shared/model/facility-party.model';

describe('Component Tests', () => {
  describe('FacilityParty Management Update Component', () => {
    let comp: FacilityPartyUpdateComponent;
    let fixture: ComponentFixture<FacilityPartyUpdateComponent>;
    let service: FacilityPartyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [FacilityPartyUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FacilityPartyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FacilityPartyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FacilityPartyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FacilityParty(123);
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
        const entity = new FacilityParty();
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
