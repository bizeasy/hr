import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmplLeave } from 'app/shared/model/empl-leave.model';
import { EmplLeaveService } from './empl-leave.service';
import { EmplLeaveDeleteDialogComponent } from './empl-leave-delete-dialog.component';

@Component({
  selector: 'sys-empl-leave',
  templateUrl: './empl-leave.component.html',
})
export class EmplLeaveComponent implements OnInit, OnDestroy {
  emplLeaves?: IEmplLeave[];
  eventSubscriber?: Subscription;

  constructor(protected emplLeaveService: EmplLeaveService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.emplLeaveService.query().subscribe((res: HttpResponse<IEmplLeave[]>) => (this.emplLeaves = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEmplLeaves();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmplLeave): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmplLeaves(): void {
    this.eventSubscriber = this.eventManager.subscribe('emplLeaveListModification', () => this.loadAll());
  }

  delete(emplLeave: IEmplLeave): void {
    const modalRef = this.modalService.open(EmplLeaveDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.emplLeave = emplLeave;
  }
}
