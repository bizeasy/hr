import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkEffortType } from 'app/shared/model/work-effort-type.model';
import { WorkEffortTypeService } from './work-effort-type.service';
import { WorkEffortTypeDeleteDialogComponent } from './work-effort-type-delete-dialog.component';

@Component({
  selector: 'sys-work-effort-type',
  templateUrl: './work-effort-type.component.html',
})
export class WorkEffortTypeComponent implements OnInit, OnDestroy {
  workEffortTypes?: IWorkEffortType[];
  eventSubscriber?: Subscription;

  constructor(
    protected workEffortTypeService: WorkEffortTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.workEffortTypeService.query().subscribe((res: HttpResponse<IWorkEffortType[]>) => (this.workEffortTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWorkEffortTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWorkEffortType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWorkEffortTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('workEffortTypeListModification', () => this.loadAll());
  }

  delete(workEffortType: IWorkEffortType): void {
    const modalRef = this.modalService.open(WorkEffortTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.workEffortType = workEffortType;
  }
}
