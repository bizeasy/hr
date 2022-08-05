import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { UserGroupMemberUpdateComponent } from 'app/entities/user-group-member/user-group-member-update.component';
import { UserGroupMemberService } from 'app/entities/user-group-member/user-group-member.service';
import { UserGroupMember } from 'app/shared/model/user-group-member.model';

describe('Component Tests', () => {
  describe('UserGroupMember Management Update Component', () => {
    let comp: UserGroupMemberUpdateComponent;
    let fixture: ComponentFixture<UserGroupMemberUpdateComponent>;
    let service: UserGroupMemberService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [UserGroupMemberUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserGroupMemberUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserGroupMemberUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserGroupMemberService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserGroupMember(123);
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
        const entity = new UserGroupMember();
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
