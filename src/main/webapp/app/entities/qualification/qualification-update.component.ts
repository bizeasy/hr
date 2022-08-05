import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQualification, Qualification } from 'app/shared/model/qualification.model';
import { QualificationService } from './qualification.service';

@Component({
  selector: 'sys-qualification-update',
  templateUrl: './qualification-update.component.html',
})
export class QualificationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [],
  });

  constructor(protected qualificationService: QualificationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qualification }) => {
      this.updateForm(qualification);
    });
  }

  updateForm(qualification: IQualification): void {
    this.editForm.patchValue({
      id: qualification.id,
      name: qualification.name,
      description: qualification.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const qualification = this.createFromForm();
    if (qualification.id !== undefined) {
      this.subscribeToSaveResponse(this.qualificationService.update(qualification));
    } else {
      this.subscribeToSaveResponse(this.qualificationService.create(qualification));
    }
  }

  private createFromForm(): IQualification {
    return {
      ...new Qualification(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQualification>>): void {
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
