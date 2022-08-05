import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PartyTypeUpdateComponent } from 'app/entities/party-type/party-type-update.component';
import { PartyTypeService } from 'app/entities/party-type/party-type.service';
import { PartyType } from 'app/shared/model/party-type.model';

describe('Component Tests', () => {
  describe('PartyType Management Update Component', () => {
    let comp: PartyTypeUpdateComponent;
    let fixture: ComponentFixture<PartyTypeUpdateComponent>;
    let service: PartyTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PartyTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartyType(123);
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
        const entity = new PartyType();
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
