import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeductionType } from 'app/shared/model/deduction-type.model';
import { DeductionTypeService } from './deduction-type.service';
import { DeductionTypeDeleteDialogComponent } from './deduction-type-delete-dialog.component';

@Component({
  selector: 'sys-deduction-type',
  templateUrl: './deduction-type.component.html',
})
export class DeductionTypeComponent implements OnInit, OnDestroy {
  deductionTypes?: IDeductionType[];
  eventSubscriber?: Subscription;

  constructor(
    protected deductionTypeService: DeductionTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.deductionTypeService.query().subscribe((res: HttpResponse<IDeductionType[]>) => (this.deductionTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDeductionTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDeductionType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDeductionTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('deductionTypeListModification', () => this.loadAll());
  }

  delete(deductionType: IDeductionType): void {
    const modalRef = this.modalService.open(DeductionTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deductionType = deductionType;
  }
}
