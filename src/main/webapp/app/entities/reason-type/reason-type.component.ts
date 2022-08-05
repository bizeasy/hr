import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReasonType } from 'app/shared/model/reason-type.model';
import { ReasonTypeService } from './reason-type.service';
import { ReasonTypeDeleteDialogComponent } from './reason-type-delete-dialog.component';

@Component({
  selector: 'sys-reason-type',
  templateUrl: './reason-type.component.html',
})
export class ReasonTypeComponent implements OnInit, OnDestroy {
  reasonTypes?: IReasonType[];
  eventSubscriber?: Subscription;

  constructor(protected reasonTypeService: ReasonTypeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.reasonTypeService.query().subscribe((res: HttpResponse<IReasonType[]>) => (this.reasonTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInReasonTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReasonType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReasonTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('reasonTypeListModification', () => this.loadAll());
  }

  delete(reasonType: IReasonType): void {
    const modalRef = this.modalService.open(ReasonTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.reasonType = reasonType;
  }
}
