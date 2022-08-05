import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyClassificationGroup } from 'app/shared/model/party-classification-group.model';

@Component({
  selector: 'sys-party-classification-group-detail',
  templateUrl: './party-classification-group-detail.component.html',
})
export class PartyClassificationGroupDetailComponent implements OnInit {
  partyClassificationGroup: IPartyClassificationGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyClassificationGroup }) => (this.partyClassificationGroup = partyClassificationGroup));
  }

  previousState(): void {
    window.history.back();
  }
}
