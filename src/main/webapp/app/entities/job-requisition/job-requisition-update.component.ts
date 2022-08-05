import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJobRequisition, JobRequisition } from 'app/shared/model/job-requisition.model';
import { JobRequisitionService } from './job-requisition.service';
import { IQualification } from 'app/shared/model/qualification.model';
import { QualificationService } from 'app/entities/qualification/qualification.service';
import { ISkillType } from 'app/shared/model/skill-type.model';
import { SkillTypeService } from 'app/entities/skill-type/skill-type.service';
import { IGeo } from 'app/shared/model/geo.model';
import { GeoService } from 'app/entities/geo/geo.service';
import { IExamType } from 'app/shared/model/exam-type.model';
import { ExamTypeService } from 'app/entities/exam-type/exam-type.service';

type SelectableEntity = IQualification | ISkillType | IGeo | IExamType;

@Component({
  selector: 'sys-job-requisition-update',
  templateUrl: './job-requisition-update.component.html',
})
export class JobRequisitionUpdateComponent implements OnInit {
  isSaving = false;
  qualifications: IQualification[] = [];
  skilltypes: ISkillType[] = [];
  geos: IGeo[] = [];
  examtypes: IExamType[] = [];
  requiredOnDateDp: any;

  editForm = this.fb.group({
    id: [],
    duration: [],
    age: [],
    gender: [],
    experienceMonths: [],
    experienceYears: [],
    qualificationStr: [null, [Validators.maxLength(60)]],
    noOfResource: [],
    requiredOnDate: [],
    qualificiation: [],
    skillType: [],
    jobLocation: [],
    examType: [],
  });

  constructor(
    protected jobRequisitionService: JobRequisitionService,
    protected qualificationService: QualificationService,
    protected skillTypeService: SkillTypeService,
    protected geoService: GeoService,
    protected examTypeService: ExamTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobRequisition }) => {
      this.updateForm(jobRequisition);

      this.qualificationService.query().subscribe((res: HttpResponse<IQualification[]>) => (this.qualifications = res.body || []));

      this.skillTypeService.query().subscribe((res: HttpResponse<ISkillType[]>) => (this.skilltypes = res.body || []));

      this.geoService.query().subscribe((res: HttpResponse<IGeo[]>) => (this.geos = res.body || []));

      this.examTypeService.query().subscribe((res: HttpResponse<IExamType[]>) => (this.examtypes = res.body || []));
    });
  }

  updateForm(jobRequisition: IJobRequisition): void {
    this.editForm.patchValue({
      id: jobRequisition.id,
      duration: jobRequisition.duration,
      age: jobRequisition.age,
      gender: jobRequisition.gender,
      experienceMonths: jobRequisition.experienceMonths,
      experienceYears: jobRequisition.experienceYears,
      qualificationStr: jobRequisition.qualificationStr,
      noOfResource: jobRequisition.noOfResource,
      requiredOnDate: jobRequisition.requiredOnDate,
      qualificiation: jobRequisition.qualificiation,
      skillType: jobRequisition.skillType,
      jobLocation: jobRequisition.jobLocation,
      examType: jobRequisition.examType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobRequisition = this.createFromForm();
    if (jobRequisition.id !== undefined) {
      this.subscribeToSaveResponse(this.jobRequisitionService.update(jobRequisition));
    } else {
      this.subscribeToSaveResponse(this.jobRequisitionService.create(jobRequisition));
    }
  }

  private createFromForm(): IJobRequisition {
    return {
      ...new JobRequisition(),
      id: this.editForm.get(['id'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      age: this.editForm.get(['age'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      experienceMonths: this.editForm.get(['experienceMonths'])!.value,
      experienceYears: this.editForm.get(['experienceYears'])!.value,
      qualificationStr: this.editForm.get(['qualificationStr'])!.value,
      noOfResource: this.editForm.get(['noOfResource'])!.value,
      requiredOnDate: this.editForm.get(['requiredOnDate'])!.value,
      qualificiation: this.editForm.get(['qualificiation'])!.value,
      skillType: this.editForm.get(['skillType'])!.value,
      jobLocation: this.editForm.get(['jobLocation'])!.value,
      examType: this.editForm.get(['examType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobRequisition>>): void {
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
