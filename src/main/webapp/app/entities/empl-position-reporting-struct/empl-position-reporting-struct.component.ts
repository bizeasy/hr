import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmplPositionReportingStruct } from 'app/shared/model/empl-position-reporting-struct.model';
import { EmplPositionReportingStructService } from './empl-position-reporting-struct.service';
import { EmplPositionReportingStructDeleteDialogComponent } from './empl-position-reporting-struct-delete-dialog.component';

@Component({
  selector: 'sys-empl-position-reporting-struct',
  templateUrl: './empl-position-reporting-struct.component.html',
})
export class EmplPositionReportingStructComponent implements OnInit, OnDestroy {
  emplPositionReportingStructs?: IEmplPositionReportingStruct[];
  eventSubscriber?: Subscription;

  constructor(
    protected emplPositionReportingStructService: EmplPositionReportingStructService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.emplPositionReportingStructService
      .query()
      .subscribe((res: HttpResponse<IEmplPositionReportingStruct[]>) => (this.emplPositionReportingStructs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEmplPositionReportingStructs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmplPositionReportingStruct): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmplPositionReportingStructs(): void {
    this.eventSubscriber = this.eventManager.subscribe('emplPositionReportingStructListModification', () => this.loadAll());
  }

  delete(emplPositionReportingStruct: IEmplPositionReportingStruct): void {
    const modalRef = this.modalService.open(EmplPositionReportingStructDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.emplPositionReportingStruct = emplPositionReportingStruct;
  }
}
