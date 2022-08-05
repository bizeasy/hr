import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITermType } from 'app/shared/model/term-type.model';
import { TermTypeService } from './term-type.service';
import { TermTypeDeleteDialogComponent } from './term-type-delete-dialog.component';

@Component({
  selector: 'sys-term-type',
  templateUrl: './term-type.component.html',
})
export class TermTypeComponent implements OnInit, OnDestroy {
  termTypes?: ITermType[];
  eventSubscriber?: Subscription;

  constructor(protected termTypeService: TermTypeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.termTypeService.query().subscribe((res: HttpResponse<ITermType[]>) => (this.termTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTermTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITermType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTermTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('termTypeListModification', () => this.loadAll());
  }

  delete(termType: ITermType): void {
    const modalRef = this.modalService.open(TermTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.termType = termType;
  }
}
