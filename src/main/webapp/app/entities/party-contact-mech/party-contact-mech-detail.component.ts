import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyContactMech } from 'app/shared/model/party-contact-mech.model';

@Component({
  selector: 'sys-party-contact-mech-detail',
  templateUrl: './party-contact-mech-detail.component.html',
})
export class PartyContactMechDetailComponent implements OnInit {
  partyContactMech: IPartyContactMech | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyContactMech }) => (this.partyContactMech = partyContactMech));
  }

  previousState(): void {
    window.history.back();
  }
}
