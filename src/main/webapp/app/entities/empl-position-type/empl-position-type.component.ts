import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmplPositionType } from 'app/shared/model/empl-position-type.model';
import { EmplPositionTypeService } from './empl-position-type.service';
import { EmplPositionTypeDeleteDialogComponent } from './empl-position-type-delete-dialog.component';

@Component({
  selector: 'sys-empl-position-type',
  templateUrl: './empl-position-type.component.html',
})
export class EmplPositionTypeComponent implements OnInit, OnDestroy {
  emplPositionTypes?: IEmplPositionType[];
  eventSubscriber?: Subscription;

  constructor(
    protected emplPositionTypeService: EmplPositionTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.emplPositionTypeService.query().subscribe((res: HttpResponse<IEmplPositionType[]>) => (this.emplPositionTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEmplPositionTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmplPositionType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmplPositionTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('emplPositionTypeListModification', () => this.loadAll());
  }

  delete(emplPositionType: IEmplPositionType): void {
    const modalRef = this.modalService.open(EmplPositionTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.emplPositionType = emplPositionType;
  }
}
