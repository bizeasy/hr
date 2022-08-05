import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEquipmentUsageLog } from 'app/shared/model/equipment-usage-log.model';
import { EquipmentUsageLogService } from './equipment-usage-log.service';
import { EquipmentUsageLogDeleteDialogComponent } from './equipment-usage-log-delete-dialog.component';

@Component({
  selector: 'sys-equipment-usage-log',
  templateUrl: './equipment-usage-log.component.html',
})
export class EquipmentUsageLogComponent implements OnInit, OnDestroy {
  equipmentUsageLogs?: IEquipmentUsageLog[];
  eventSubscriber?: Subscription;

  constructor(
    protected equipmentUsageLogService: EquipmentUsageLogService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.equipmentUsageLogService
      .query()
      .subscribe((res: HttpResponse<IEquipmentUsageLog[]>) => (this.equipmentUsageLogs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEquipmentUsageLogs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEquipmentUsageLog): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEquipmentUsageLogs(): void {
    this.eventSubscriber = this.eventManager.subscribe('equipmentUsageLogListModification', () => this.loadAll());
  }

  delete(equipmentUsageLog: IEquipmentUsageLog): void {
    const modalRef = this.modalService.open(EquipmentUsageLogDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.equipmentUsageLog = equipmentUsageLog;
  }
}
