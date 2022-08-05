import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInvoiceItemType, InvoiceItemType } from 'app/shared/model/invoice-item-type.model';
import { InvoiceItemTypeService } from './invoice-item-type.service';

@Component({
  selector: 'sys-invoice-item-type-update',
  templateUrl: './invoice-item-type-update.component.html',
})
export class InvoiceItemTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(100)]],
  });

  constructor(
    protected invoiceItemTypeService: InvoiceItemTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoiceItemType }) => {
      this.updateForm(invoiceItemType);
    });
  }

  updateForm(invoiceItemType: IInvoiceItemType): void {
    this.editForm.patchValue({
      id: invoiceItemType.id,
      name: invoiceItemType.name,
      description: invoiceItemType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoiceItemType = this.createFromForm();
    if (invoiceItemType.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceItemTypeService.update(invoiceItemType));
    } else {
      this.subscribeToSaveResponse(this.invoiceItemTypeService.create(invoiceItemType));
    }
  }

  private createFromForm(): IInvoiceItemType {
    return {
      ...new InvoiceItemType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceItemType>>): void {
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
}
