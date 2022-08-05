import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUom, Uom } from 'app/shared/model/uom.model';
import { UomService } from './uom.service';
import { IUomType } from 'app/shared/model/uom-type.model';
import { UomTypeService } from 'app/entities/uom-type/uom-type.service';

@Component({
  selector: 'sys-uom-update',
  templateUrl: './uom-update.component.html',
})
export class UomUpdateComponent implements OnInit {
  isSaving = false;
  uomtypes: IUomType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(100)]],
    sequenceNo: [],
    abbreviation: [null, [Validators.maxLength(10)]],
    uomType: [],
  });

  constructor(
    protected uomService: UomService,
    protected uomTypeService: UomTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uom }) => {
      this.updateForm(uom);

      this.uomTypeService.query().subscribe((res: HttpResponse<IUomType[]>) => (this.uomtypes = res.body || []));
    });
  }

  updateForm(uom: IUom): void {
    this.editForm.patchValue({
      id: uom.id,
      name: uom.name,
      description: uom.description,
      sequenceNo: uom.sequenceNo,
      abbreviation: uom.abbreviation,
      uomType: uom.uomType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const uom = this.createFromForm();
    if (uom.id !== undefined) {
      this.subscribeToSaveResponse(this.uomService.update(uom));
    } else {
      this.subscribeToSaveResponse(this.uomService.create(uom));
    }
  }

  private createFromForm(): IUom {
    return {
      ...new Uom(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      abbreviation: this.editForm.get(['abbreviation'])!.value,
      uomType: this.editForm.get(['uomType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUom>>): void {
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

  trackById(index: number, item: IUomType): any {
    return item.id;
  }
}
