import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILocationType, LocationType } from 'app/shared/model/location-type.model';
import { LocationTypeService } from './location-type.service';

@Component({
  selector: 'sys-location-type-update',
  templateUrl: './location-type-update.component.html',
})
export class LocationTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(100)]],
  });

  constructor(protected locationTypeService: LocationTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ locationType }) => {
      this.updateForm(locationType);
    });
  }

  updateForm(locationType: ILocationType): void {
    this.editForm.patchValue({
      id: locationType.id,
      name: locationType.name,
      description: locationType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const locationType = this.createFromForm();
    if (locationType.id !== undefined) {
      this.subscribeToSaveResponse(this.locationTypeService.update(locationType));
    } else {
      this.subscribeToSaveResponse(this.locationTypeService.create(locationType));
    }
  }

  private createFromForm(): ILocationType {
    return {
      ...new LocationType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocationType>>): void {
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
