import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaxAuthority } from 'app/shared/model/tax-authority.model';
import { TaxAuthorityService } from './tax-authority.service';
import { TaxAuthorityDeleteDialogComponent } from './tax-authority-delete-dialog.component';

@Component({
  selector: 'sys-tax-authority',
  templateUrl: './tax-authority.component.html',
})
export class TaxAuthorityComponent implements OnInit, OnDestroy {
  taxAuthorities?: ITaxAuthority[];
  eventSubscriber?: Subscription;

  constructor(
    protected taxAuthorityService: TaxAuthorityService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.taxAuthorityService.query().subscribe((res: HttpResponse<ITaxAuthority[]>) => (this.taxAuthorities = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTaxAuthorities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITaxAuthority): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTaxAuthorities(): void {
    this.eventSubscriber = this.eventManager.subscribe('taxAuthorityListModification', () => this.loadAll());
  }

  delete(taxAuthority: ITaxAuthority): void {
    const modalRef = this.modalService.open(TaxAuthorityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taxAuthority = taxAuthority;
  }
}
