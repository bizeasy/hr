import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { FacilityGroupMemberDetailComponent } from 'app/entities/facility-group-member/facility-group-member-detail.component';
import { FacilityGroupMember } from 'app/shared/model/facility-group-member.model';

describe('Component Tests', () => {
  describe('FacilityGroupMember Management Detail Component', () => {
    let comp: FacilityGroupMemberDetailComponent;
    let fixture: ComponentFixture<FacilityGroupMemberDetailComponent>;
    const route = ({ data: of({ facilityGroupMember: new FacilityGroupMember(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [FacilityGroupMemberDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FacilityGroupMemberDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FacilityGroupMemberDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load facilityGroupMember on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.facilityGroupMember).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
