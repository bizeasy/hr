import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductStoreType } from 'app/shared/model/product-store-type.model';
import { ProductStoreTypeService } from './product-store-type.service';
import { ProductStoreTypeDeleteDialogComponent } from './product-store-type-delete-dialog.component';

@Component({
  selector: 'sys-product-store-type',
  templateUrl: './product-store-type.component.html',
})
export class ProductStoreTypeComponent implements OnInit, OnDestroy {
  productStoreTypes?: IProductStoreType[];
  eventSubscriber?: Subscription;

  constructor(
    protected productStoreTypeService: ProductStoreTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.productStoreTypeService.query().subscribe((res: HttpResponse<IProductStoreType[]>) => (this.productStoreTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProductStoreTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProductStoreType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProductStoreTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('productStoreTypeListModification', () => this.loadAll());
  }

  delete(productStoreType: IProductStoreType): void {
    const modalRef = this.modalService.open(ProductStoreTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productStoreType = productStoreType;
  }
}
