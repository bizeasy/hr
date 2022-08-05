import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatalogueCategoryType } from 'app/shared/model/catalogue-category-type.model';

@Component({
  selector: 'sys-catalogue-category-type-detail',
  templateUrl: './catalogue-category-type-detail.component.html',
})
export class CatalogueCategoryTypeDetailComponent implements OnInit {
  catalogueCategoryType: ICatalogueCategoryType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catalogueCategoryType }) => (this.catalogueCategoryType = catalogueCategoryType));
  }

  previousState(): void {
    window.history.back();
  }
}
