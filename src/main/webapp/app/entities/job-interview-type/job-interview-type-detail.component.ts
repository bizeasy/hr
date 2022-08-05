import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobInterviewType } from 'app/shared/model/job-interview-type.model';

@Component({
  selector: 'sys-job-interview-type-detail',
  templateUrl: './job-interview-type-detail.component.html',
})
export class JobInterviewTypeDetailComponent implements OnInit {
  jobInterviewType: IJobInterviewType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobInterviewType }) => (this.jobInterviewType = jobInterviewType));
  }

  previousState(): void {
    window.history.back();
  }
}
