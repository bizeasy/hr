import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITaxAuthorityRateType, TaxAuthorityRateType } from 'app/shared/model/tax-authority-rate-type.model';
import { TaxAuthorityRateTypeService } from './tax-authority-rate-type.service';
import { ITaxAuthority } from 'app/shared/model/tax-authority.model';
import { TaxAuthorityService } from 'app/entities/tax-authority/tax-authority.service';
import { ITaxSlab } from 'app/shared/model/tax-slab.model';
import { TaxSlabService } from 'app/entities/tax-slab/tax-slab.service';

type SelectableEntity = ITaxAuthority | ITaxSlab;

@Component({
  selector: 'sys-tax-authority-rate-type-update',
  templateUrl: './tax-authority-rate-type-update.component.html',
})
export class TaxAuthorityRateTypeUpdateComponent implements OnInit {
  isSaving = false;
  taxauthorities: ITaxAuthority[] = [];
  taxslabs: ITaxSlab[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(60)]],
    taxAuthority: [],
    taxSlab: [],
  });

  constructor(
    protected taxAuthorityRateTypeService: TaxAuthorityRateTypeService,
    protected taxAuthorityService: TaxAuthorityService,
    protected taxSlabService: TaxSlabService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taxAuthorityRateType }) => {
      this.updateForm(taxAuthorityRateType);

      this.taxAuthorityService.query().subscribe((res: HttpResponse<ITaxAuthority[]>) => (this.taxauthorities = res.body || []));

      this.taxSlabService.query().subscribe((res: HttpResponse<ITaxSlab[]>) => (this.taxslabs = res.body || []));
    });
  }

  updateForm(taxAuthorityRateType: ITaxAuthorityRateType): void {
    this.editForm.patchValue({
      id: taxAuthorityRateType.id,
      name: taxAuthorityRateType.name,
      description: taxAuthorityRateType.description,
      taxAuthority: taxAuthorityRateType.taxAuthority,
      taxSlab: taxAuthorityRateType.taxSlab,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taxAuthorityRateType = this.createFromForm();
    if (taxAuthorityRateType.id !== undefined) {
      this.subscribeToSaveResponse(this.taxAuthorityRateTypeService.update(taxAuthorityRateType));
    } else {
      this.subscribeToSaveResponse(this.taxAuthorityRateTypeService.create(taxAuthorityRateType));
    }
  }

  private createFromForm(): ITaxAuthorityRateType {
    return {
      ...new TaxAuthorityRateType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      taxAuthority: this.editForm.get(['taxAuthority'])!.value,
      taxSlab: this.editForm.get(['taxSlab'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaxAuthorityRateType>>): void {
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
