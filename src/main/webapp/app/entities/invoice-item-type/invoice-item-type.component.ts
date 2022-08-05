import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInvoiceItemType } from 'app/shared/model/invoice-item-type.model';
import { InvoiceItemTypeService } from './invoice-item-type.service';
import { InvoiceItemTypeDeleteDialogComponent } from './invoice-item-type-delete-dialog.component';

@Component({
  selector: 'sys-invoice-item-type',
  templateUrl: './invoice-item-type.component.html',
})
export class InvoiceItemTypeComponent implements OnInit, OnDestroy {
  invoiceItemTypes?: IInvoiceItemType[];
  eventSubscriber?: Subscription;

  constructor(
    protected invoiceItemTypeService: InvoiceItemTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.invoiceItemTypeService.query().subscribe((res: HttpResponse<IInvoiceItemType[]>) => (this.invoiceItemTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInvoiceItemTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInvoiceItemType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInvoiceItemTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('invoiceItemTypeListModification', () => this.loadAll());
  }

  delete(invoiceItemType: IInvoiceItemType): void {
    const modalRef = this.modalService.open(InvoiceItemTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.invoiceItemType = invoiceItemType;
  }
}
