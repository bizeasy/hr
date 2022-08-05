import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IInvoiceType } from 'app/shared/model/invoice-type.model';
import { InvoiceTypeService } from './invoice-type.service';
import { InvoiceTypeDeleteDialogComponent } from './invoice-type-delete-dialog.component';

@Component({
  selector: 'sys-invoice-type',
  templateUrl: './invoice-type.component.html',
})
export class InvoiceTypeComponent implements OnInit, OnDestroy {
  invoiceTypes?: IInvoiceType[];
  eventSubscriber?: Subscription;

  constructor(
    protected invoiceTypeService: InvoiceTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.invoiceTypeService.query().subscribe((res: HttpResponse<IInvoiceType[]>) => (this.invoiceTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInInvoiceTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IInvoiceType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInInvoiceTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('invoiceTypeListModification', () => this.loadAll());
  }

  delete(invoiceType: IInvoiceType): void {
    const modalRef = this.modalService.open(InvoiceTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.invoiceType = invoiceType;
  }
}
