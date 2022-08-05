import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaxAuthority } from 'app/shared/model/tax-authority.model';

@Component({
  selector: 'sys-tax-authority-detail',
  templateUrl: './tax-authority-detail.component.html',
})
export class TaxAuthorityDetailComponent implements OnInit {
  taxAuthority: ITaxAuthority | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taxAuthority }) => (this.taxAuthority = taxAuthority));
  }

  previousState(): void {
    window.history.back();
  }
}
