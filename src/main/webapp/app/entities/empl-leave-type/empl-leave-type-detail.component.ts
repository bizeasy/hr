import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmplLeaveType } from 'app/shared/model/empl-leave-type.model';

@Component({
  selector: 'sys-empl-leave-type-detail',
  templateUrl: './empl-leave-type-detail.component.html',
})
export class EmplLeaveTypeDetailComponent implements OnInit {
  emplLeaveType: IEmplLeaveType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplLeaveType }) => (this.emplLeaveType = emplLeaveType));
  }

  previousState(): void {
    window.history.back();
  }
}
