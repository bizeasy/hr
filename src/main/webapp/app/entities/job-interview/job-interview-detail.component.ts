import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobInterview } from 'app/shared/model/job-interview.model';

@Component({
  selector: 'sys-job-interview-detail',
  templateUrl: './job-interview-detail.component.html',
})
export class JobInterviewDetailComponent implements OnInit {
  jobInterview: IJobInterview | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobInterview }) => (this.jobInterview = jobInterview));
  }

  previousState(): void {
    window.history.back();
  }
}
