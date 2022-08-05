import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmplLeaveReasonType } from 'app/shared/model/empl-leave-reason-type.model';
import { EmplLeaveReasonTypeService } from './empl-leave-reason-type.service';
import { EmplLeaveReasonTypeDeleteDialogComponent } from './empl-leave-reason-type-delete-dialog.component';

@Component({
  selector: 'sys-empl-leave-reason-type',
  templateUrl: './empl-leave-reason-type.component.html',
})
export class EmplLeaveReasonTypeComponent implements OnInit, OnDestroy {
  emplLeaveReasonTypes?: IEmplLeaveReasonType[];
  eventSubscriber?: Subscription;

  constructor(
    protected emplLeaveReasonTypeService: EmplLeaveReasonTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.emplLeaveReasonTypeService
      .query()
      .subscribe((res: HttpResponse<IEmplLeaveReasonType[]>) => (this.emplLeaveReasonTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEmplLeaveReasonTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmplLeaveReasonType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmplLeaveReasonTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('emplLeaveReasonTypeListModification', () => this.loadAll());
  }

  delete(emplLeaveReasonType: IEmplLeaveReasonType): void {
    const modalRef = this.modalService.open(EmplLeaveReasonTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.emplLeaveReasonType = emplLeaveReasonType;
  }
}
