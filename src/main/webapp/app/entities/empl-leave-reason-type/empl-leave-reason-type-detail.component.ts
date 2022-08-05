import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmplLeaveReasonType } from 'app/shared/model/empl-leave-reason-type.model';

@Component({
  selector: 'sys-empl-leave-reason-type-detail',
  templateUrl: './empl-leave-reason-type-detail.component.html',
})
export class EmplLeaveReasonTypeDetailComponent implements OnInit {
  emplLeaveReasonType: IEmplLeaveReasonType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplLeaveReasonType }) => (this.emplLeaveReasonType = emplLeaveReasonType));
  }

  previousState(): void {
    window.history.back();
  }
}
