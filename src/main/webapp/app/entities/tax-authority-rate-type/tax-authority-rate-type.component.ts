import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaxAuthorityRateType } from 'app/shared/model/tax-authority-rate-type.model';
import { TaxAuthorityRateTypeService } from './tax-authority-rate-type.service';
import { TaxAuthorityRateTypeDeleteDialogComponent } from './tax-authority-rate-type-delete-dialog.component';

@Component({
  selector: 'sys-tax-authority-rate-type',
  templateUrl: './tax-authority-rate-type.component.html',
})
export class TaxAuthorityRateTypeComponent implements OnInit, OnDestroy {
  taxAuthorityRateTypes?: ITaxAuthorityRateType[];
  eventSubscriber?: Subscription;

  constructor(
    protected taxAuthorityRateTypeService: TaxAuthorityRateTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.taxAuthorityRateTypeService
      .query()
      .subscribe((res: HttpResponse<ITaxAuthorityRateType[]>) => (this.taxAuthorityRateTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTaxAuthorityRateTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITaxAuthorityRateType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTaxAuthorityRateTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('taxAuthorityRateTypeListModification', () => this.loadAll());
  }

  delete(taxAuthorityRateType: ITaxAuthorityRateType): void {
    const modalRef = this.modalService.open(TaxAuthorityRateTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taxAuthorityRateType = taxAuthorityRateType;
  }
}
