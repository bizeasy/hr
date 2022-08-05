import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPayrollPreference } from 'app/shared/model/payroll-preference.model';
import { PayrollPreferenceService } from './payroll-preference.service';
import { PayrollPreferenceDeleteDialogComponent } from './payroll-preference-delete-dialog.component';

@Component({
  selector: 'sys-payroll-preference',
  templateUrl: './payroll-preference.component.html',
})
export class PayrollPreferenceComponent implements OnInit, OnDestroy {
  payrollPreferences?: IPayrollPreference[];
  eventSubscriber?: Subscription;

  constructor(
    protected payrollPreferenceService: PayrollPreferenceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.payrollPreferenceService
      .query()
      .subscribe((res: HttpResponse<IPayrollPreference[]>) => (this.payrollPreferences = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPayrollPreferences();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPayrollPreference): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPayrollPreferences(): void {
    this.eventSubscriber = this.eventManager.subscribe('payrollPreferenceListModification', () => this.loadAll());
  }

  delete(payrollPreference: IPayrollPreference): void {
    const modalRef = this.modalService.open(PayrollPreferenceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.payrollPreference = payrollPreference;
  }
}
