import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { UserGroupAuthorityDetailComponent } from 'app/entities/user-group-authority/user-group-authority-detail.component';
import { UserGroupAuthority } from 'app/shared/model/user-group-authority.model';

describe('Component Tests', () => {
  describe('UserGroupAuthority Management Detail Component', () => {
    let comp: UserGroupAuthorityDetailComponent;
    let fixture: ComponentFixture<UserGroupAuthorityDetailComponent>;
    const route = ({ data: of({ userGroupAuthority: new UserGroupAuthority(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [UserGroupAuthorityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserGroupAuthorityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserGroupAuthorityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userGroupAuthority on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userGroupAuthority).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
