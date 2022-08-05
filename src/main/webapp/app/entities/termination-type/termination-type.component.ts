import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITerminationType } from 'app/shared/model/termination-type.model';
import { TerminationTypeService } from './termination-type.service';
import { TerminationTypeDeleteDialogComponent } from './termination-type-delete-dialog.component';

@Component({
  selector: 'sys-termination-type',
  templateUrl: './termination-type.component.html',
})
export class TerminationTypeComponent implements OnInit, OnDestroy {
  terminationTypes?: ITerminationType[];
  eventSubscriber?: Subscription;

  constructor(
    protected terminationTypeService: TerminationTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.terminationTypeService.query().subscribe((res: HttpResponse<ITerminationType[]>) => (this.terminationTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTerminationTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITerminationType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTerminationTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('terminationTypeListModification', () => this.loadAll());
  }

  delete(terminationType: ITerminationType): void {
    const modalRef = this.modalService.open(TerminationTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.terminationType = terminationType;
  }
}
