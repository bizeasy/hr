import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFacilityGroupMember } from 'app/shared/model/facility-group-member.model';

@Component({
  selector: 'sys-facility-group-member-detail',
  templateUrl: './facility-group-member-detail.component.html',
})
export class FacilityGroupMemberDetailComponent implements OnInit {
  facilityGroupMember: IFacilityGroupMember | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facilityGroupMember }) => (this.facilityGroupMember = facilityGroupMember));
  }

  previousState(): void {
    window.history.back();
  }
}
