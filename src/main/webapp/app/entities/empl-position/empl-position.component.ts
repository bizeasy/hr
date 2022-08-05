import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmplPosition } from 'app/shared/model/empl-position.model';
import { EmplPositionService } from './empl-position.service';
import { EmplPositionDeleteDialogComponent } from './empl-position-delete-dialog.component';

@Component({
  selector: 'sys-empl-position',
  templateUrl: './empl-position.component.html',
})
export class EmplPositionComponent implements OnInit, OnDestroy {
  emplPositions?: IEmplPosition[];
  eventSubscriber?: Subscription;

  constructor(
    protected emplPositionService: EmplPositionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.emplPositionService.query().subscribe((res: HttpResponse<IEmplPosition[]>) => (this.emplPositions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEmplPositions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmplPosition): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmplPositions(): void {
    this.eventSubscriber = this.eventManager.subscribe('emplPositionListModification', () => this.loadAll());
  }

  delete(emplPosition: IEmplPosition): void {
    const modalRef = this.modalService.open(EmplPositionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.emplPosition = emplPosition;
  }
}
