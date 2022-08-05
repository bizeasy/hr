import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaxSlab } from 'app/shared/model/tax-slab.model';
import { TaxSlabService } from './tax-slab.service';
import { TaxSlabDeleteDialogComponent } from './tax-slab-delete-dialog.component';

@Component({
  selector: 'sys-tax-slab',
  templateUrl: './tax-slab.component.html',
})
export class TaxSlabComponent implements OnInit, OnDestroy {
  taxSlabs?: ITaxSlab[];
  eventSubscriber?: Subscription;

  constructor(protected taxSlabService: TaxSlabService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.taxSlabService.query().subscribe((res: HttpResponse<ITaxSlab[]>) => (this.taxSlabs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTaxSlabs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITaxSlab): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTaxSlabs(): void {
    this.eventSubscriber = this.eventManager.subscribe('taxSlabListModification', () => this.loadAll());
  }

  delete(taxSlab: ITaxSlab): void {
    const modalRef = this.modalService.open(TaxSlabDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taxSlab = taxSlab;
  }
}
