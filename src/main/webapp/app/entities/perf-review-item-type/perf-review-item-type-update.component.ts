import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPerfReviewItemType, PerfReviewItemType } from 'app/shared/model/perf-review-item-type.model';
import { PerfReviewItemTypeService } from './perf-review-item-type.service';

@Component({
  selector: 'sys-perf-review-item-type-update',
  templateUrl: './perf-review-item-type-update.component.html',
})
export class PerfReviewItemTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [],
  });

  constructor(
    protected perfReviewItemTypeService: PerfReviewItemTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfReviewItemType }) => {
      this.updateForm(perfReviewItemType);
    });
  }

  updateForm(perfReviewItemType: IPerfReviewItemType): void {
    this.editForm.patchValue({
      id: perfReviewItemType.id,
      name: perfReviewItemType.name,
      description: perfReviewItemType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const perfReviewItemType = this.createFromForm();
    if (perfReviewItemType.id !== undefined) {
      this.subscribeToSaveResponse(this.perfReviewItemTypeService.update(perfReviewItemType));
    } else {
      this.subscribeToSaveResponse(this.perfReviewItemTypeService.create(perfReviewItemType));
    }
  }

  private createFromForm(): IPerfReviewItemType {
    return {
      ...new PerfReviewItemType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerfReviewItemType>>): void {
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
