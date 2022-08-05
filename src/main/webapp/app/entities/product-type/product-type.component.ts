import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductType } from 'app/shared/model/product-type.model';
import { ProductTypeService } from './product-type.service';
import { ProductTypeDeleteDialogComponent } from './product-type-delete-dialog.component';

@Component({
  selector: 'sys-product-type',
  templateUrl: './product-type.component.html',
})
export class ProductTypeComponent implements OnInit, OnDestroy {
  productTypes?: IProductType[];
  eventSubscriber?: Subscription;

  constructor(
    protected productTypeService: ProductTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.productTypeService.query().subscribe((res: HttpResponse<IProductType[]>) => (this.productTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProductTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProductType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProductTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('productTypeListModification', () => this.loadAll());
  }

  delete(productType: IProductType): void {
    const modalRef = this.modalService.open(ProductTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productType = productType;
  }
}
