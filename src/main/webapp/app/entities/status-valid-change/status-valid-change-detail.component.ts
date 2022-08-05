import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IStatusValidChange } from 'app/shared/model/status-valid-change.model';

@Component({
  selector: 'sys-status-valid-change-detail',
  templateUrl: './status-valid-change-detail.component.html',
})
export class StatusValidChangeDetailComponent implements OnInit {
  statusValidChange: IStatusValidChange | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusValidChange }) => (this.statusValidChange = statusValidChange));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
