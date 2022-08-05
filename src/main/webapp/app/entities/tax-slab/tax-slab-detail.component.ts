import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaxSlab } from 'app/shared/model/tax-slab.model';

@Component({
  selector: 'sys-tax-slab-detail',
  templateUrl: './tax-slab-detail.component.html',
})
export class TaxSlabDetailComponent implements OnInit {
  taxSlab: ITaxSlab | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taxSlab }) => (this.taxSlab = taxSlab));
  }

  previousState(): void {
    window.history.back();
  }
}
