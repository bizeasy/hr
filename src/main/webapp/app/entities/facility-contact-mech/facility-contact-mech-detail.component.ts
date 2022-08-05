import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFacilityContactMech } from 'app/shared/model/facility-contact-mech.model';

@Component({
  selector: 'sys-facility-contact-mech-detail',
  templateUrl: './facility-contact-mech-detail.component.html',
})
export class FacilityContactMechDetailComponent implements OnInit {
  facilityContactMech: IFacilityContactMech | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facilityContactMech }) => (this.facilityContactMech = facilityContactMech));
  }

  previousState(): void {
    window.history.back();
  }
}
