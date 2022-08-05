import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvoiceItemType } from 'app/shared/model/invoice-item-type.model';

@Component({
  selector: 'sys-invoice-item-type-detail',
  templateUrl: './invoice-item-type-detail.component.html',
})
export class InvoiceItemTypeDetailComponent implements OnInit {
  invoiceItemType: IInvoiceItemType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoiceItemType }) => (this.invoiceItemType = invoiceItemType));
  }

  previousState(): void {
    window.history.back();
  }
}
