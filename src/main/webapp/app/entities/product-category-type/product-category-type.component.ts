import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductCategoryType } from 'app/shared/model/product-category-type.model';
import { ProductCategoryTypeService } from './product-category-type.service';
import { ProductCategoryTypeDeleteDialogComponent } from './product-category-type-delete-dialog.component';

@Component({
  selector: 'sys-product-category-type',
  templateUrl: './product-category-type.component.html',
})
export class ProductCategoryTypeComponent implements OnInit, OnDestroy {
  productCategoryTypes?: IProductCategoryType[];
  eventSubscriber?: Subscription;

  constructor(
    protected productCategoryTypeService: ProductCategoryTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.productCategoryTypeService
      .query()
      .subscribe((res: HttpResponse<IProductCategoryType[]>) => (this.productCategoryTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProductCategoryTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProductCategoryType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProductCategoryTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('productCategoryTypeListModification', () => this.loadAll());
  }

  delete(productCategoryType: IProductCategoryType): void {
    const modalRef = this.modalService.open(ProductCategoryTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productCategoryType = productCategoryType;
  }
}
