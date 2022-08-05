import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITermType } from 'app/shared/model/term-type.model';

@Component({
  selector: 'sys-term-type-detail',
  templateUrl: './term-type-detail.component.html',
})
export class TermTypeDetailComponent implements OnInit {
  termType: ITermType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ termType }) => (this.termType = termType));
  }

  previousState(): void {
    window.history.back();
  }
}
