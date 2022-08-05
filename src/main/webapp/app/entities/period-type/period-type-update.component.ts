import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPeriodType, PeriodType } from 'app/shared/model/period-type.model';
import { PeriodTypeService } from './period-type.service';
import { IUom } from 'app/shared/model/uom.model';
import { UomService } from 'app/entities/uom/uom.service';

@Component({
  selector: 'sys-period-type-update',
  templateUrl: './period-type-update.component.html',
})
export class PeriodTypeUpdateComponent implements OnInit {
  isSaving = false;
  uoms: IUom[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [],
    periodLength: [],
    uom: [],
  });

  constructor(
    protected periodTypeService: PeriodTypeService,
    protected uomService: UomService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ periodType }) => {
      this.updateForm(periodType);

      this.uomService.query().subscribe((res: HttpResponse<IUom[]>) => (this.uoms = res.body || []));
    });
  }

  updateForm(periodType: IPeriodType): void {
    this.editForm.patchValue({
      id: periodType.id,
      name: periodType.name,
      description: periodType.description,
      periodLength: periodType.periodLength,
      uom: periodType.uom,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const periodType = this.createFromForm();
    if (periodType.id !== undefined) {
      this.subscribeToSaveResponse(this.periodTypeService.update(periodType));
    } else {
      this.subscribeToSaveResponse(this.periodTypeService.create(periodType));
    }
  }

  private createFromForm(): IPeriodType {
    return {
      ...new PeriodType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      periodLength: this.editForm.get(['periodLength'])!.value,
      uom: this.editForm.get(['uom'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeriodType>>): void {
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

  trackById(index: number, item: IUom): any {
    return item.id;
  }
}
