import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJobInterviewType, JobInterviewType } from 'app/shared/model/job-interview-type.model';
import { JobInterviewTypeService } from './job-interview-type.service';

@Component({
  selector: 'sys-job-interview-type-update',
  templateUrl: './job-interview-type-update.component.html',
})
export class JobInterviewTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [],
  });

  constructor(
    protected jobInterviewTypeService: JobInterviewTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobInterviewType }) => {
      this.updateForm(jobInterviewType);
    });
  }

  updateForm(jobInterviewType: IJobInterviewType): void {
    this.editForm.patchValue({
      id: jobInterviewType.id,
      name: jobInterviewType.name,
      description: jobInterviewType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobInterviewType = this.createFromForm();
    if (jobInterviewType.id !== undefined) {
      this.subscribeToSaveResponse(this.jobInterviewTypeService.update(jobInterviewType));
    } else {
      this.subscribeToSaveResponse(this.jobInterviewTypeService.create(jobInterviewType));
    }
  }

  private createFromForm(): IJobInterviewType {
    return {
      ...new JobInterviewType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobInterviewType>>): void {
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
