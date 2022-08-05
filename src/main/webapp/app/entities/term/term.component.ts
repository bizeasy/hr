import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITerm } from 'app/shared/model/term.model';
import { TermService } from './term.service';
import { TermDeleteDialogComponent } from './term-delete-dialog.component';

@Component({
  selector: 'sys-term',
  templateUrl: './term.component.html',
})
export class TermComponent implements OnInit, OnDestroy {
  terms?: ITerm[];
  eventSubscriber?: Subscription;

  constructor(protected termService: TermService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.termService.query().subscribe((res: HttpResponse<ITerm[]>) => (this.terms = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTerms();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITerm): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTerms(): void {
    this.eventSubscriber = this.eventManager.subscribe('termListModification', () => this.loadAll());
  }

  delete(term: ITerm): void {
    const modalRef = this.modalService.open(TermDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.term = term;
  }
}
