import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICatalogue } from 'app/shared/model/catalogue.model';

@Component({
  selector: 'sys-catalogue-detail',
  templateUrl: './catalogue-detail.component.html',
})
export class CatalogueDetailComponent implements OnInit {
  catalogue: ICatalogue | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catalogue }) => (this.catalogue = catalogue));
  }

  previousState(): void {
    window.history.back();
  }
}
