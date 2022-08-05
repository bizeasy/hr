import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmploymentAppSourceType, EmploymentAppSourceType } from 'app/shared/model/employment-app-source-type.model';
import { EmploymentAppSourceTypeService } from './employment-app-source-type.service';

@Component({
  selector: 'sys-employment-app-source-type-update',
  templateUrl: './employment-app-source-type-update.component.html',
})
export class EmploymentAppSourceTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [],
  });

  constructor(
    protected employmentAppSourceTypeService: EmploymentAppSourceTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employmentAppSourceType }) => {
      this.updateForm(employmentAppSourceType);
    });
  }

  updateForm(employmentAppSourceType: IEmploymentAppSourceType): void {
    this.editForm.patchValue({
      id: employmentAppSourceType.id,
      name: employmentAppSourceType.name,
      description: employmentAppSourceType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employmentAppSourceType = this.createFromForm();
    if (employmentAppSourceType.id !== undefined) {
      this.subscribeToSaveResponse(this.employmentAppSourceTypeService.update(employmentAppSourceType));
    } else {
      this.subscribeToSaveResponse(this.employmentAppSourceTypeService.create(employmentAppSourceType));
    }
  }

  private createFromForm(): IEmploymentAppSourceType {
    return {
      ...new EmploymentAppSourceType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmploymentAppSourceType>>): void {
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
