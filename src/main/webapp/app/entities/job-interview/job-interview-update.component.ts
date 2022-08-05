import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IJobInterview, JobInterview } from 'app/shared/model/job-interview.model';
import { JobInterviewService } from './job-interview.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IJobInterviewType } from 'app/shared/model/job-interview-type.model';
import { JobInterviewTypeService } from 'app/entities/job-interview-type/job-interview-type.service';
import { IJobRequisition } from 'app/shared/model/job-requisition.model';
import { JobRequisitionService } from 'app/entities/job-requisition/job-requisition.service';
import { IInterviewGrade } from 'app/shared/model/interview-grade.model';
import { InterviewGradeService } from 'app/entities/interview-grade/interview-grade.service';
import { IInterviewResult } from 'app/shared/model/interview-result.model';
import { InterviewResultService } from 'app/entities/interview-result/interview-result.service';

type SelectableEntity = IParty | IJobInterviewType | IJobRequisition | IInterviewGrade | IInterviewResult;

@Component({
  selector: 'sys-job-interview-update',
  templateUrl: './job-interview-update.component.html',
})
export class JobInterviewUpdateComponent implements OnInit {
  isSaving = false;
  parties: IParty[] = [];
  jobinterviewtypes: IJobInterviewType[] = [];
  jobrequisitions: IJobRequisition[] = [];
  interviewgrades: IInterviewGrade[] = [];
  interviewresults: IInterviewResult[] = [];

  editForm = this.fb.group({
    id: [],
    remarks: [],
    interviewDate: [],
    interviewee: [],
    interviewer: [],
    type: [],
    jobRequisition: [],
    gradeSecured: [],
    result: [],
  });

  constructor(
    protected jobInterviewService: JobInterviewService,
    protected partyService: PartyService,
    protected jobInterviewTypeService: JobInterviewTypeService,
    protected jobRequisitionService: JobRequisitionService,
    protected interviewGradeService: InterviewGradeService,
    protected interviewResultService: InterviewResultService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobInterview }) => {
      if (!jobInterview.id) {
        const today = moment().startOf('day');
        jobInterview.interviewDate = today;
      }

      this.updateForm(jobInterview);

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.jobInterviewTypeService.query().subscribe((res: HttpResponse<IJobInterviewType[]>) => (this.jobinterviewtypes = res.body || []));

      this.jobRequisitionService.query().subscribe((res: HttpResponse<IJobRequisition[]>) => (this.jobrequisitions = res.body || []));

      this.interviewGradeService.query().subscribe((res: HttpResponse<IInterviewGrade[]>) => (this.interviewgrades = res.body || []));

      this.interviewResultService.query().subscribe((res: HttpResponse<IInterviewResult[]>) => (this.interviewresults = res.body || []));
    });
  }

  updateForm(jobInterview: IJobInterview): void {
    this.editForm.patchValue({
      id: jobInterview.id,
      remarks: jobInterview.remarks,
      interviewDate: jobInterview.interviewDate ? jobInterview.interviewDate.format(DATE_TIME_FORMAT) : null,
      interviewee: jobInterview.interviewee,
      interviewer: jobInterview.interviewer,
      type: jobInterview.type,
      jobRequisition: jobInterview.jobRequisition,
      gradeSecured: jobInterview.gradeSecured,
      result: jobInterview.result,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobInterview = this.createFromForm();
    if (jobInterview.id !== undefined) {
      this.subscribeToSaveResponse(this.jobInterviewService.update(jobInterview));
    } else {
      this.subscribeToSaveResponse(this.jobInterviewService.create(jobInterview));
    }
  }

  private createFromForm(): IJobInterview {
    return {
      ...new JobInterview(),
      id: this.editForm.get(['id'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      interviewDate: this.editForm.get(['interviewDate'])!.value
        ? moment(this.editForm.get(['interviewDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      interviewee: this.editForm.get(['interviewee'])!.value,
      interviewer: this.editForm.get(['interviewer'])!.value,
      type: this.editForm.get(['type'])!.value,
      jobRequisition: this.editForm.get(['jobRequisition'])!.value,
      gradeSecured: this.editForm.get(['gradeSecured'])!.value,
      result: this.editForm.get(['result'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobInterview>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
