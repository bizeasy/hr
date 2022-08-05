import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContentType } from 'app/shared/model/content-type.model';
import { ContentTypeService } from './content-type.service';
import { ContentTypeDeleteDialogComponent } from './content-type-delete-dialog.component';

@Component({
  selector: 'sys-content-type',
  templateUrl: './content-type.component.html',
})
export class ContentTypeComponent implements OnInit, OnDestroy {
  contentTypes?: IContentType[];
  eventSubscriber?: Subscription;

  constructor(
    protected contentTypeService: ContentTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.contentTypeService.query().subscribe((res: HttpResponse<IContentType[]>) => (this.contentTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInContentTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContentType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContentTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('contentTypeListModification', () => this.loadAll());
  }

  delete(contentType: IContentType): void {
    const modalRef = this.modalService.open(ContentTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contentType = contentType;
  }
}
