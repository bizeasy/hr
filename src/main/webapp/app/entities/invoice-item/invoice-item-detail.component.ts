import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInvoiceItem } from 'app/shared/model/invoice-item.model';

@Component({
  selector: 'sys-invoice-item-detail',
  templateUrl: './invoice-item-detail.component.html',
})
export class InvoiceItemDetailComponent implements OnInit {
  invoiceItem: IInvoiceItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoiceItem }) => (this.invoiceItem = invoiceItem));
  }

  previousState(): void {
    window.history.back();
  }
}
