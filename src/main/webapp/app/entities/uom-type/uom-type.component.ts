import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUomType } from 'app/shared/model/uom-type.model';
import { UomTypeService } from './uom-type.service';
import { UomTypeDeleteDialogComponent } from './uom-type-delete-dialog.component';

@Component({
  selector: 'sys-uom-type',
  templateUrl: './uom-type.component.html',
})
export class UomTypeComponent implements OnInit, OnDestroy {
  uomTypes?: IUomType[];
  eventSubscriber?: Subscription;

  constructor(protected uomTypeService: UomTypeService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.uomTypeService.query().subscribe((res: HttpResponse<IUomType[]>) => (this.uomTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUomTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUomType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUomTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('uomTypeListModification', () => this.loadAll());
  }

  delete(uomType: IUomType): void {
    const modalRef = this.modalService.open(UomTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.uomType = uomType;
  }
}
