import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInterviewGrade } from 'app/shared/model/interview-grade.model';

@Component({
  selector: 'sys-interview-grade-detail',
  templateUrl: './interview-grade-detail.component.html',
})
export class InterviewGradeDetailComponent implements OnInit {
  interviewGrade: IInterviewGrade | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interviewGrade }) => (this.interviewGrade = interviewGrade));
  }

  previousState(): void {
    window.history.back();
  }
}
