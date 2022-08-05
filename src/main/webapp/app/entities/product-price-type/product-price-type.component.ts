import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductPriceType } from 'app/shared/model/product-price-type.model';
import { ProductPriceTypeService } from './product-price-type.service';
import { ProductPriceTypeDeleteDialogComponent } from './product-price-type-delete-dialog.component';

@Component({
  selector: 'sys-product-price-type',
  templateUrl: './product-price-type.component.html',
})
export class ProductPriceTypeComponent implements OnInit, OnDestroy {
  productPriceTypes?: IProductPriceType[];
  eventSubscriber?: Subscription;

  constructor(
    protected productPriceTypeService: ProductPriceTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.productPriceTypeService.query().subscribe((res: HttpResponse<IProductPriceType[]>) => (this.productPriceTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProductPriceTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProductPriceType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProductPriceTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('productPriceTypeListModification', () => this.loadAll());
  }

  delete(productPriceType: IProductPriceType): void {
    const modalRef = this.modalService.open(ProductPriceTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productPriceType = productPriceType;
  }
}
