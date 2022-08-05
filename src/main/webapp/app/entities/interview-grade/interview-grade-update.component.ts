import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInterviewGrade, InterviewGrade } from 'app/shared/model/interview-grade.model';
import { InterviewGradeService } from './interview-grade.service';

@Component({
  selector: 'sys-interview-grade-update',
  templateUrl: './interview-grade-update.component.html',
})
export class InterviewGradeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [],
  });

  constructor(protected interviewGradeService: InterviewGradeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ interviewGrade }) => {
      this.updateForm(interviewGrade);
    });
  }

  updateForm(interviewGrade: IInterviewGrade): void {
    this.editForm.patchValue({
      id: interviewGrade.id,
      name: interviewGrade.name,
      description: interviewGrade.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const interviewGrade = this.createFromForm();
    if (interviewGrade.id !== undefined) {
      this.subscribeToSaveResponse(this.interviewGradeService.update(interviewGrade));
    } else {
      this.subscribeToSaveResponse(this.interviewGradeService.create(interviewGrade));
    }
  }

  private createFromForm(): IInterviewGrade {
    return {
      ...new InterviewGrade(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInterviewGrade>>): void {
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
