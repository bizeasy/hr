import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { UserGroupAuthorityUpdateComponent } from 'app/entities/user-group-authority/user-group-authority-update.component';
import { UserGroupAuthorityService } from 'app/entities/user-group-authority/user-group-authority.service';
import { UserGroupAuthority } from 'app/shared/model/user-group-authority.model';

describe('Component Tests', () => {
  describe('UserGroupAuthority Management Update Component', () => {
    let comp: UserGroupAuthorityUpdateComponent;
    let fixture: ComponentFixture<UserGroupAuthorityUpdateComponent>;
    let service: UserGroupAuthorityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [UserGroupAuthorityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserGroupAuthorityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserGroupAuthorityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserGroupAuthorityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserGroupAuthority(123);
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
        const entity = new UserGroupAuthority();
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
