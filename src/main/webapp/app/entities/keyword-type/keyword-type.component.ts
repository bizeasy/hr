import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IKeywordType } from 'app/shared/model/keyword-type.model';
import { KeywordTypeService } from './keyword-type.service';
import { KeywordTypeDeleteDialogComponent } from './keyword-type-delete-dialog.component';

@Component({
  selector: 'sys-keyword-type',
  templateUrl: './keyword-type.component.html',
})
export class KeywordTypeComponent implements OnInit, OnDestroy {
  keywordTypes?: IKeywordType[];
  eventSubscriber?: Subscription;

  constructor(
    protected keywordTypeService: KeywordTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.keywordTypeService.query().subscribe((res: HttpResponse<IKeywordType[]>) => (this.keywordTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInKeywordTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IKeywordType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInKeywordTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('keywordTypeListModification', () => this.loadAll());
  }

  delete(keywordType: IKeywordType): void {
    const modalRef = this.modalService.open(KeywordTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.keywordType = keywordType;
  }
}
