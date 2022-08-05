import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPerfRatingType, PerfRatingType } from 'app/shared/model/perf-rating-type.model';
import { PerfRatingTypeService } from './perf-rating-type.service';

@Component({
  selector: 'sys-perf-rating-type-update',
  templateUrl: './perf-rating-type-update.component.html',
})
export class PerfRatingTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [],
  });

  constructor(protected perfRatingTypeService: PerfRatingTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ perfRatingType }) => {
      this.updateForm(perfRatingType);
    });
  }

  updateForm(perfRatingType: IPerfRatingType): void {
    this.editForm.patchValue({
      id: perfRatingType.id,
      name: perfRatingType.name,
      description: perfRatingType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const perfRatingType = this.createFromForm();
    if (perfRatingType.id !== undefined) {
      this.subscribeToSaveResponse(this.perfRatingTypeService.update(perfRatingType));
    } else {
      this.subscribeToSaveResponse(this.perfRatingTypeService.create(perfRatingType));
    }
  }

  private createFromForm(): IPerfRatingType {
    return {
      ...new PerfRatingType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPerfRatingType>>): void {
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
