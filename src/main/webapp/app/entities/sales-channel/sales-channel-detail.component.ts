import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalesChannel } from 'app/shared/model/sales-channel.model';

@Component({
  selector: 'sys-sales-channel-detail',
  templateUrl: './sales-channel-detail.component.html',
})
export class SalesChannelDetailComponent implements OnInit {
  salesChannel: ISalesChannel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salesChannel }) => (this.salesChannel = salesChannel));
  }

  previousState(): void {
    window.history.back();
  }
}
