import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IInvoice, Invoice } from 'app/shared/model/invoice.model';
import { InvoiceService } from './invoice.service';
import { IInvoiceType } from 'app/shared/model/invoice-type.model';
import { InvoiceTypeService } from 'app/entities/invoice-type/invoice-type.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IRoleType } from 'app/shared/model/role-type.model';
import { RoleTypeService } from 'app/entities/role-type/role-type.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';
import { IContactMech } from 'app/shared/model/contact-mech.model';
import { ContactMechService } from 'app/entities/contact-mech/contact-mech.service';

type SelectableEntity = IInvoiceType | IParty | IRoleType | IStatus | IContactMech;

@Component({
  selector: 'sys-invoice-update',
  templateUrl: './invoice-update.component.html',
})
export class InvoiceUpdateComponent implements OnInit {
  isSaving = false;
  invoicetypes: IInvoiceType[] = [];
  parties: IParty[] = [];
  roletypes: IRoleType[] = [];
  statuses: IStatus[] = [];
  contactmeches: IContactMech[] = [];

  editForm = this.fb.group({
    id: [],
    invoiceDate: [],
    dueDate: [],
    paidDate: [],
    invoiceMessage: [null, [Validators.maxLength(255)]],
    referenceNumber: [null, [Validators.maxLength(60)]],
    invoiceType: [],
    partyIdFrom: [],
    partyIdTo: [],
    roleType: [],
    status: [],
    contactMech: [],
  });

  constructor(
    protected invoiceService: InvoiceService,
    protected invoiceTypeService: InvoiceTypeService,
    protected partyService: PartyService,
    protected roleTypeService: RoleTypeService,
    protected statusService: StatusService,
    protected contactMechService: ContactMechService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoice }) => {
      if (!invoice.id) {
        const today = moment().startOf('day');
        invoice.invoiceDate = today;
        invoice.dueDate = today;
        invoice.paidDate = today;
      }

      this.updateForm(invoice);

      this.invoiceTypeService.query().subscribe((res: HttpResponse<IInvoiceType[]>) => (this.invoicetypes = res.body || []));

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.roleTypeService.query().subscribe((res: HttpResponse<IRoleType[]>) => (this.roletypes = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));

      this.contactMechService.query().subscribe((res: HttpResponse<IContactMech[]>) => (this.contactmeches = res.body || []));
    });
  }

  updateForm(invoice: IInvoice): void {
    this.editForm.patchValue({
      id: invoice.id,
      invoiceDate: invoice.invoiceDate ? invoice.invoiceDate.format(DATE_TIME_FORMAT) : null,
      dueDate: invoice.dueDate ? invoice.dueDate.format(DATE_TIME_FORMAT) : null,
      paidDate: invoice.paidDate ? invoice.paidDate.format(DATE_TIME_FORMAT) : null,
      invoiceMessage: invoice.invoiceMessage,
      referenceNumber: invoice.referenceNumber,
      invoiceType: invoice.invoiceType,
      partyIdFrom: invoice.partyIdFrom,
      partyIdTo: invoice.partyIdTo,
      roleType: invoice.roleType,
      status: invoice.status,
      contactMech: invoice.contactMech,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoice = this.createFromForm();
    if (invoice.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceService.update(invoice));
    } else {
      this.subscribeToSaveResponse(this.invoiceService.create(invoice));
    }
  }

  private createFromForm(): IInvoice {
    return {
      ...new Invoice(),
      id: this.editForm.get(['id'])!.value,
      invoiceDate: this.editForm.get(['invoiceDate'])!.value
        ? moment(this.editForm.get(['invoiceDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dueDate: this.editForm.get(['dueDate'])!.value ? moment(this.editForm.get(['dueDate'])!.value, DATE_TIME_FORMAT) : undefined,
      paidDate: this.editForm.get(['paidDate'])!.value ? moment(this.editForm.get(['paidDate'])!.value, DATE_TIME_FORMAT) : undefined,
      invoiceMessage: this.editForm.get(['invoiceMessage'])!.value,
      referenceNumber: this.editForm.get(['referenceNumber'])!.value,
      invoiceType: this.editForm.get(['invoiceType'])!.value,
      partyIdFrom: this.editForm.get(['partyIdFrom'])!.value,
      partyIdTo: this.editForm.get(['partyIdTo'])!.value,
      roleType: this.editForm.get(['roleType'])!.value,
      status: this.editForm.get(['status'])!.value,
      contactMech: this.editForm.get(['contactMech'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoice>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
