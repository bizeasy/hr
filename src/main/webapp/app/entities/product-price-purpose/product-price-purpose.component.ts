import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductPricePurpose } from 'app/shared/model/product-price-purpose.model';
import { ProductPricePurposeService } from './product-price-purpose.service';
import { ProductPricePurposeDeleteDialogComponent } from './product-price-purpose-delete-dialog.component';

@Component({
  selector: 'sys-product-price-purpose',
  templateUrl: './product-price-purpose.component.html',
})
export class ProductPricePurposeComponent implements OnInit, OnDestroy {
  productPricePurposes?: IProductPricePurpose[];
  eventSubscriber?: Subscription;

  constructor(
    protected productPricePurposeService: ProductPricePurposeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.productPricePurposeService
      .query()
      .subscribe((res: HttpResponse<IProductPricePurpose[]>) => (this.productPricePurposes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProductPricePurposes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProductPricePurpose): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProductPricePurposes(): void {
    this.eventSubscriber = this.eventManager.subscribe('productPricePurposeListModification', () => this.loadAll());
  }

  delete(productPricePurpose: IProductPricePurpose): void {
    const modalRef = this.modalService.open(ProductPricePurposeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productPricePurpose = productPricePurpose;
  }
}
