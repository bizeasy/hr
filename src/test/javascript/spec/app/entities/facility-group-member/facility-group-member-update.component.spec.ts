import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { FacilityGroupMemberUpdateComponent } from 'app/entities/facility-group-member/facility-group-member-update.component';
import { FacilityGroupMemberService } from 'app/entities/facility-group-member/facility-group-member.service';
import { FacilityGroupMember } from 'app/shared/model/facility-group-member.model';

describe('Component Tests', () => {
  describe('FacilityGroupMember Management Update Component', () => {
    let comp: FacilityGroupMemberUpdateComponent;
    let fixture: ComponentFixture<FacilityGroupMemberUpdateComponent>;
    let service: FacilityGroupMemberService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [FacilityGroupMemberUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FacilityGroupMemberUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FacilityGroupMemberUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FacilityGroupMemberService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FacilityGroupMember(123);
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
        const entity = new FacilityGroupMember();
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
