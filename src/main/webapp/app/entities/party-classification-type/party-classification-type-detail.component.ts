import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartyClassificationType } from 'app/shared/model/party-classification-type.model';

@Component({
  selector: 'sys-party-classification-type-detail',
  templateUrl: './party-classification-type-detail.component.html',
})
export class PartyClassificationTypeDetailComponent implements OnInit {
  partyClassificationType: IPartyClassificationType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyClassificationType }) => (this.partyClassificationType = partyClassificationType));
  }

  previousState(): void {
    window.history.back();
  }
}
