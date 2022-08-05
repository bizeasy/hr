import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITerm } from 'app/shared/model/term.model';

@Component({
  selector: 'sys-term-detail',
  templateUrl: './term-detail.component.html',
})
export class TermDetailComponent implements OnInit {
  term: ITerm | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ term }) => (this.term = term));
  }

  previousState(): void {
    window.history.back();
  }
}
