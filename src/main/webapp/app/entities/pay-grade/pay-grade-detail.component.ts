import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPayGrade } from 'app/shared/model/pay-grade.model';

@Component({
  selector: 'sys-pay-grade-detail',
  templateUrl: './pay-grade-detail.component.html',
})
export class PayGradeDetailComponent implements OnInit {
  payGrade: IPayGrade | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payGrade }) => (this.payGrade = payGrade));
  }

  previousState(): void {
    window.history.back();
  }
}
