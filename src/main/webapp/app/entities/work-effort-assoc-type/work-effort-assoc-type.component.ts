import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkEffortAssocType } from 'app/shared/model/work-effort-assoc-type.model';
import { WorkEffortAssocTypeService } from './work-effort-assoc-type.service';
import { WorkEffortAssocTypeDeleteDialogComponent } from './work-effort-assoc-type-delete-dialog.component';

@Component({
  selector: 'sys-work-effort-assoc-type',
  templateUrl: './work-effort-assoc-type.component.html',
})
export class WorkEffortAssocTypeComponent implements OnInit, OnDestroy {
  workEffortAssocTypes?: IWorkEffortAssocType[];
  eventSubscriber?: Subscription;

  constructor(
    protected workEffortAssocTypeService: WorkEffortAssocTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.workEffortAssocTypeService
      .query()
      .subscribe((res: HttpResponse<IWorkEffortAssocType[]>) => (this.workEffortAssocTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInWorkEffortAssocTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IWorkEffortAssocType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInWorkEffortAssocTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('workEffortAssocTypeListModification', () => this.loadAll());
  }

  delete(workEffortAssocType: IWorkEffortAssocType): void {
    const modalRef = this.modalService.open(WorkEffortAssocTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.workEffortAssocType = workEffortAssocType;
  }
}
