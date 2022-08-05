import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFacilityEquipment } from 'app/shared/model/facility-equipment.model';
import { FacilityEquipmentService } from './facility-equipment.service';
import { FacilityEquipmentDeleteDialogComponent } from './facility-equipment-delete-dialog.component';

@Component({
  selector: 'sys-facility-equipment',
  templateUrl: './facility-equipment.component.html',
})
export class FacilityEquipmentComponent implements OnInit, OnDestroy {
  facilityEquipments?: IFacilityEquipment[];
  eventSubscriber?: Subscription;

  constructor(
    protected facilityEquipmentService: FacilityEquipmentService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.facilityEquipmentService
      .query()
      .subscribe((res: HttpResponse<IFacilityEquipment[]>) => (this.facilityEquipments = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFacilityEquipments();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFacilityEquipment): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFacilityEquipments(): void {
    this.eventSubscriber = this.eventManager.subscribe('facilityEquipmentListModification', () => this.loadAll());
  }

  delete(facilityEquipment: IFacilityEquipment): void {
    const modalRef = this.modalService.open(FacilityEquipmentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.facilityEquipment = facilityEquipment;
  }
}
