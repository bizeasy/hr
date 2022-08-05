import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommunicationEventType } from 'app/shared/model/communication-event-type.model';

@Component({
  selector: 'sys-communication-event-type-detail',
  templateUrl: './communication-event-type-detail.component.html',
})
export class CommunicationEventTypeDetailComponent implements OnInit {
  communicationEventType: ICommunicationEventType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ communicationEventType }) => (this.communicationEventType = communicationEventType));
  }

  previousState(): void {
    window.history.back();
  }
}
