import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemIssuance } from 'app/shared/model/item-issuance.model';

@Component({
  selector: 'sys-item-issuance-detail',
  templateUrl: './item-issuance-detail.component.html',
})
export class ItemIssuanceDetailComponent implements OnInit {
  itemIssuance: IItemIssuance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemIssuance }) => (this.itemIssuance = itemIssuance));
  }

  previousState(): void {
    window.history.back();
  }
}
