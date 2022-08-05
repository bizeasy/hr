import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { RoleTypeComponent } from 'app/entities/role-type/role-type.component';
import { RoleTypeService } from 'app/entities/role-type/role-type.service';
import { RoleType } from 'app/shared/model/role-type.model';

describe('Component Tests', () => {
  describe('RoleType Management Component', () => {
    let comp: RoleTypeComponent;
    let fixture: ComponentFixture<RoleTypeComponent>;
    let service: RoleTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [RoleTypeComponent],
      })
        .overrideTemplate(RoleTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RoleTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RoleTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RoleType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.roleTypes && comp.roleTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
