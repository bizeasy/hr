import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILocationType } from 'app/shared/model/location-type.model';
import { LocationTypeService } from './location-type.service';
import { LocationTypeDeleteDialogComponent } from './location-type-delete-dialog.component';

@Component({
  selector: 'sys-location-type',
  templateUrl: './location-type.component.html',
})
export class LocationTypeComponent implements OnInit, OnDestroy {
  locationTypes?: ILocationType[];
  eventSubscriber?: Subscription;

  constructor(
    protected locationTypeService: LocationTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.locationTypeService.query().subscribe((res: HttpResponse<ILocationType[]>) => (this.locationTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLocationTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILocationType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLocationTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('locationTypeListModification', () => this.loadAll());
  }

  delete(locationType: ILocationType): void {
    const modalRef = this.modalService.open(LocationTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.locationType = locationType;
  }
}
