import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatalogueCategory } from 'app/shared/model/catalogue-category.model';

@Component({
  selector: 'sys-catalogue-category-detail',
  templateUrl: './catalogue-category-detail.component.html',
})
export class CatalogueCategoryDetailComponent implements OnInit {
  catalogueCategory: ICatalogueCategory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catalogueCategory }) => (this.catalogueCategory = catalogueCategory));
  }

  previousState(): void {
    window.history.back();
  }
}
