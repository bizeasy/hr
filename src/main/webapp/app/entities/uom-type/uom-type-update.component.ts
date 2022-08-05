import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUomType, UomType } from 'app/shared/model/uom-type.model';
import { UomTypeService } from './uom-type.service';

@Component({
  selector: 'sys-uom-type-update',
  templateUrl: './uom-type-update.component.html',
})
export class UomTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(100)]],
  });

  constructor(protected uomTypeService: UomTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uomType }) => {
      this.updateForm(uomType);
    });
  }

  updateForm(uomType: IUomType): void {
    this.editForm.patchValue({
      id: uomType.id,
      name: uomType.name,
      description: uomType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const uomType = this.createFromForm();
    if (uomType.id !== undefined) {
      this.subscribeToSaveResponse(this.uomTypeService.update(uomType));
    } else {
      this.subscribeToSaveResponse(this.uomTypeService.create(uomType));
    }
  }

  private createFromForm(): IUomType {
    return {
      ...new UomType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUomType>>): void {
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
