import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { UserGroupMemberDetailComponent } from 'app/entities/user-group-member/user-group-member-detail.component';
import { UserGroupMember } from 'app/shared/model/user-group-member.model';

describe('Component Tests', () => {
  describe('UserGroupMember Management Detail Component', () => {
    let comp: UserGroupMemberDetailComponent;
    let fixture: ComponentFixture<UserGroupMemberDetailComponent>;
    const route = ({ data: of({ userGroupMember: new UserGroupMember(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [UserGroupMemberDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserGroupMemberDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserGroupMemberDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userGroupMember on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userGroupMember).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
