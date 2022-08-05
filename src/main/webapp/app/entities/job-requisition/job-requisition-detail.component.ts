import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobRequisition } from 'app/shared/model/job-requisition.model';

@Component({
  selector: 'sys-job-requisition-detail',
  templateUrl: './job-requisition-detail.component.html',
})
export class JobRequisitionDetailComponent implements OnInit {
  jobRequisition: IJobRequisition | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobRequisition }) => (this.jobRequisition = jobRequisition));
  }

  previousState(): void {
    window.history.back();
  }
}
