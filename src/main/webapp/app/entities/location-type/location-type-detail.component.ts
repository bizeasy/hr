import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILocationType } from 'app/shared/model/location-type.model';

@Component({
  selector: 'sys-location-type-detail',
  templateUrl: './location-type-detail.component.html',
})
export class LocationTypeDetailComponent implements OnInit {
  locationType: ILocationType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ locationType }) => (this.locationType = locationType));
  }

  previousState(): void {
    window.history.back();
  }
}
