import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PartyRoleUpdateComponent } from 'app/entities/party-role/party-role-update.component';
import { PartyRoleService } from 'app/entities/party-role/party-role.service';
import { PartyRole } from 'app/shared/model/party-role.model';

describe('Component Tests', () => {
  describe('PartyRole Management Update Component', () => {
    let comp: PartyRoleUpdateComponent;
    let fixture: ComponentFixture<PartyRoleUpdateComponent>;
    let service: PartyRoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyRoleUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PartyRoleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartyRoleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartyRoleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PartyRole(123);
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
        const entity = new PartyRole();
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
