import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvoiceType } from 'app/shared/model/invoice-type.model';

@Component({
  selector: 'sys-invoice-type-detail',
  templateUrl: './invoice-type-detail.component.html',
})
export class InvoiceTypeDetailComponent implements OnInit {
  invoiceType: IInvoiceType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoiceType }) => (this.invoiceType = invoiceType));
  }

  previousState(): void {
    window.history.back();
  }
}
