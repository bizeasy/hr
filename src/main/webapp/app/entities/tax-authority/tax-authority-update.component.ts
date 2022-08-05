import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITaxAuthority, TaxAuthority } from 'app/shared/model/tax-authority.model';
import { TaxAuthorityService } from './tax-authority.service';

@Component({
  selector: 'sys-tax-authority-update',
  templateUrl: './tax-authority-update.component.html',
})
export class TaxAuthorityUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
  });

  constructor(protected taxAuthorityService: TaxAuthorityService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taxAuthority }) => {
      this.updateForm(taxAuthority);
    });
  }

  updateForm(taxAuthority: ITaxAuthority): void {
    this.editForm.patchValue({
      id: taxAuthority.id,
      name: taxAuthority.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taxAuthority = this.createFromForm();
    if (taxAuthority.id !== undefined) {
      this.subscribeToSaveResponse(this.taxAuthorityService.update(taxAuthority));
    } else {
      this.subscribeToSaveResponse(this.taxAuthorityService.create(taxAuthority));
    }
  }

  private createFromForm(): ITaxAuthority {
    return {
      ...new TaxAuthority(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaxAuthority>>): void {
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
