import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITaxSlab, TaxSlab } from 'app/shared/model/tax-slab.model';
import { TaxSlabService } from './tax-slab.service';

@Component({
  selector: 'sys-tax-slab-update',
  templateUrl: './tax-slab-update.component.html',
})
export class TaxSlabUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    rate: [],
  });

  constructor(protected taxSlabService: TaxSlabService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taxSlab }) => {
      this.updateForm(taxSlab);
    });
  }

  updateForm(taxSlab: ITaxSlab): void {
    this.editForm.patchValue({
      id: taxSlab.id,
      name: taxSlab.name,
      rate: taxSlab.rate,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taxSlab = this.createFromForm();
    if (taxSlab.id !== undefined) {
      this.subscribeToSaveResponse(this.taxSlabService.update(taxSlab));
    } else {
      this.subscribeToSaveResponse(this.taxSlabService.create(taxSlab));
    }
  }

  private createFromForm(): ITaxSlab {
    return {
      ...new TaxSlab(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      rate: this.editForm.get(['rate'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaxSlab>>): void {
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
