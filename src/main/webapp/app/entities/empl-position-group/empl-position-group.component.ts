import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmplPositionGroup } from 'app/shared/model/empl-position-group.model';
import { EmplPositionGroupService } from './empl-position-group.service';
import { EmplPositionGroupDeleteDialogComponent } from './empl-position-group-delete-dialog.component';

@Component({
  selector: 'sys-empl-position-group',
  templateUrl: './empl-position-group.component.html',
})
export class EmplPositionGroupComponent implements OnInit, OnDestroy {
  emplPositionGroups?: IEmplPositionGroup[];
  eventSubscriber?: Subscription;

  constructor(
    protected emplPositionGroupService: EmplPositionGroupService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.emplPositionGroupService
      .query()
      .subscribe((res: HttpResponse<IEmplPositionGroup[]>) => (this.emplPositionGroups = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEmplPositionGroups();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmplPositionGroup): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmplPositionGroups(): void {
    this.eventSubscriber = this.eventManager.subscribe('emplPositionGroupListModification', () => this.loadAll());
  }

  delete(emplPositionGroup: IEmplPositionGroup): void {
    const modalRef = this.modalService.open(EmplPositionGroupDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.emplPositionGroup = emplPositionGroup;
  }
}
