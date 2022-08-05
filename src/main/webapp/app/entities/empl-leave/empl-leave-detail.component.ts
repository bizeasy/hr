import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmplLeave } from 'app/shared/model/empl-leave.model';

@Component({
  selector: 'sys-empl-leave-detail',
  templateUrl: './empl-leave-detail.component.html',
})
export class EmplLeaveDetailComponent implements OnInit {
  emplLeave: IEmplLeave | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplLeave }) => (this.emplLeave = emplLeave));
  }

  previousState(): void {
    window.history.back();
  }
}
