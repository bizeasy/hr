import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PermissionAuthorityUpdateComponent } from 'app/entities/permission-authority/permission-authority-update.component';
import { PermissionAuthorityService } from 'app/entities/permission-authority/permission-authority.service';
import { PermissionAuthority } from 'app/shared/model/permission-authority.model';

describe('Component Tests', () => {
  describe('PermissionAuthority Management Update Component', () => {
    let comp: PermissionAuthorityUpdateComponent;
    let fixture: ComponentFixture<PermissionAuthorityUpdateComponent>;
    let service: PermissionAuthorityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PermissionAuthorityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PermissionAuthorityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PermissionAuthorityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PermissionAuthorityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PermissionAuthority(123);
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
        const entity = new PermissionAuthority();
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
