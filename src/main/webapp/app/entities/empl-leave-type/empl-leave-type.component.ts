import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmplLeaveType } from 'app/shared/model/empl-leave-type.model';
import { EmplLeaveTypeService } from './empl-leave-type.service';
import { EmplLeaveTypeDeleteDialogComponent } from './empl-leave-type-delete-dialog.component';

@Component({
  selector: 'sys-empl-leave-type',
  templateUrl: './empl-leave-type.component.html',
})
export class EmplLeaveTypeComponent implements OnInit, OnDestroy {
  emplLeaveTypes?: IEmplLeaveType[];
  eventSubscriber?: Subscription;

  constructor(
    protected emplLeaveTypeService: EmplLeaveTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.emplLeaveTypeService.query().subscribe((res: HttpResponse<IEmplLeaveType[]>) => (this.emplLeaveTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEmplLeaveTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmplLeaveType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmplLeaveTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('emplLeaveTypeListModification', () => this.loadAll());
  }

  delete(emplLeaveType: IEmplLeaveType): void {
    const modalRef = this.modalService.open(EmplLeaveTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.emplLeaveType = emplLeaveType;
  }
}
