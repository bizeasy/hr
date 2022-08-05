import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInvoiceType, InvoiceType } from 'app/shared/model/invoice-type.model';
import { InvoiceTypeService } from './invoice-type.service';

@Component({
  selector: 'sys-invoice-type-update',
  templateUrl: './invoice-type-update.component.html',
})
export class InvoiceTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(100)]],
  });

  constructor(protected invoiceTypeService: InvoiceTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ invoiceType }) => {
      this.updateForm(invoiceType);
    });
  }

  updateForm(invoiceType: IInvoiceType): void {
    this.editForm.patchValue({
      id: invoiceType.id,
      name: invoiceType.name,
      description: invoiceType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const invoiceType = this.createFromForm();
    if (invoiceType.id !== undefined) {
      this.subscribeToSaveResponse(this.invoiceTypeService.update(invoiceType));
    } else {
      this.subscribeToSaveResponse(this.invoiceTypeService.create(invoiceType));
    }
  }

  private createFromForm(): IInvoiceType {
    return {
      ...new InvoiceType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoiceType>>): void {
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
