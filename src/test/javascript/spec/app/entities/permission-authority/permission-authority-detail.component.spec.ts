import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PermissionAuthorityDetailComponent } from 'app/entities/permission-authority/permission-authority-detail.component';
import { PermissionAuthority } from 'app/shared/model/permission-authority.model';

describe('Component Tests', () => {
  describe('PermissionAuthority Management Detail Component', () => {
    let comp: PermissionAuthorityDetailComponent;
    let fixture: ComponentFixture<PermissionAuthorityDetailComponent>;
    const route = ({ data: of({ permissionAuthority: new PermissionAuthority(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PermissionAuthorityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PermissionAuthorityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PermissionAuthorityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load permissionAuthority on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.permissionAuthority).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
