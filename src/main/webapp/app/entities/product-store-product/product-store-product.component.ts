import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductStoreProduct } from 'app/shared/model/product-store-product.model';
import { ProductStoreProductService } from './product-store-product.service';
import { ProductStoreProductDeleteDialogComponent } from './product-store-product-delete-dialog.component';

@Component({
  selector: 'sys-product-store-product',
  templateUrl: './product-store-product.component.html',
})
export class ProductStoreProductComponent implements OnInit, OnDestroy {
  productStoreProducts?: IProductStoreProduct[];
  eventSubscriber?: Subscription;

  constructor(
    protected productStoreProductService: ProductStoreProductService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.productStoreProductService
      .query()
      .subscribe((res: HttpResponse<IProductStoreProduct[]>) => (this.productStoreProducts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProductStoreProducts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProductStoreProduct): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProductStoreProducts(): void {
    this.eventSubscriber = this.eventManager.subscribe('productStoreProductListModification', () => this.loadAll());
  }

  delete(productStoreProduct: IProductStoreProduct): void {
    const modalRef = this.modalService.open(ProductStoreProductDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productStoreProduct = productStoreProduct;
  }
}
