import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductKeyword } from 'app/shared/model/product-keyword.model';
import { ProductKeywordService } from './product-keyword.service';
import { ProductKeywordDeleteDialogComponent } from './product-keyword-delete-dialog.component';

@Component({
  selector: 'sys-product-keyword',
  templateUrl: './product-keyword.component.html',
})
export class ProductKeywordComponent implements OnInit, OnDestroy {
  productKeywords?: IProductKeyword[];
  eventSubscriber?: Subscription;

  constructor(
    protected productKeywordService: ProductKeywordService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.productKeywordService.query().subscribe((res: HttpResponse<IProductKeyword[]>) => (this.productKeywords = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProductKeywords();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProductKeyword): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProductKeywords(): void {
    this.eventSubscriber = this.eventManager.subscribe('productKeywordListModification', () => this.loadAll());
  }

  delete(productKeyword: IProductKeyword): void {
    const modalRef = this.modalService.open(ProductKeywordDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productKeyword = productKeyword;
  }
}
