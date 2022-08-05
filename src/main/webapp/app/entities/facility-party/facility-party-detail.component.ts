import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFacilityParty } from 'app/shared/model/facility-party.model';

@Component({
  selector: 'sys-facility-party-detail',
  templateUrl: './facility-party-detail.component.html',
})
export class FacilityPartyDetailComponent implements OnInit {
  facilityParty: IFacilityParty | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facilityParty }) => (this.facilityParty = facilityParty));
  }

  previousState(): void {
    window.history.back();
  }
}
