import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICatalogueCategoryType } from 'app/shared/model/catalogue-category-type.model';
import { CatalogueCategoryTypeService } from './catalogue-category-type.service';
import { CatalogueCategoryTypeDeleteDialogComponent } from './catalogue-category-type-delete-dialog.component';

@Component({
  selector: 'sys-catalogue-category-type',
  templateUrl: './catalogue-category-type.component.html',
})
export class CatalogueCategoryTypeComponent implements OnInit, OnDestroy {
  catalogueCategoryTypes?: ICatalogueCategoryType[];
  eventSubscriber?: Subscription;

  constructor(
    protected catalogueCategoryTypeService: CatalogueCategoryTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.catalogueCategoryTypeService
      .query()
      .subscribe((res: HttpResponse<ICatalogueCategoryType[]>) => (this.catalogueCategoryTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCatalogueCategoryTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICatalogueCategoryType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCatalogueCategoryTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('catalogueCategoryTypeListModification', () => this.loadAll());
  }

  delete(catalogueCategoryType: ICatalogueCategoryType): void {
    const modalRef = this.modalService.open(CatalogueCategoryTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.catalogueCategoryType = catalogueCategoryType;
  }
}
