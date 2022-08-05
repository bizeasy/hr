import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKeywordType } from 'app/shared/model/keyword-type.model';

@Component({
  selector: 'sys-keyword-type-detail',
  templateUrl: './keyword-type-detail.component.html',
})
export class KeywordTypeDetailComponent implements OnInit {
  keywordType: IKeywordType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ keywordType }) => (this.keywordType = keywordType));
  }

  previousState(): void {
    window.history.back();
  }
}
