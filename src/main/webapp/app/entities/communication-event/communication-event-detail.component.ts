import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICommunicationEvent } from 'app/shared/model/communication-event.model';

@Component({
  selector: 'sys-communication-event-detail',
  templateUrl: './communication-event-detail.component.html',
})
export class CommunicationEventDetailComponent implements OnInit {
  communicationEvent: ICommunicationEvent | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ communicationEvent }) => (this.communicationEvent = communicationEvent));
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
