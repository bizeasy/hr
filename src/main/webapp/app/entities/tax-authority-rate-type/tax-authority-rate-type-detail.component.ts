import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaxAuthorityRateType } from 'app/shared/model/tax-authority-rate-type.model';

@Component({
  selector: 'sys-tax-authority-rate-type-detail',
  templateUrl: './tax-authority-rate-type-detail.component.html',
})
export class TaxAuthorityRateTypeDetailComponent implements OnInit {
  taxAuthorityRateType: ITaxAuthorityRateType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taxAuthorityRateType }) => (this.taxAuthorityRateType = taxAuthorityRateType));
  }

  previousState(): void {
    window.history.back();
  }
}
