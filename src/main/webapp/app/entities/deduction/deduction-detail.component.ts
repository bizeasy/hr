import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeduction } from 'app/shared/model/deduction.model';

@Component({
  selector: 'sys-deduction-detail',
  templateUrl: './deduction-detail.component.html',
})
export class DeductionDetailComponent implements OnInit {
  deduction: IDeduction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deduction }) => (this.deduction = deduction));
  }

  previousState(): void {
    window.history.back();
  }
}
