import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEmploymentApp, EmploymentApp } from 'app/shared/model/employment-app.model';
import { EmploymentAppService } from './employment-app.service';
import { IEmplPosition } from 'app/shared/model/empl-position.model';
import { EmplPositionService } from 'app/entities/empl-position/empl-position.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';
import { IEmploymentAppSourceType } from 'app/shared/model/employment-app-source-type.model';
import { EmploymentAppSourceTypeService } from 'app/entities/employment-app-source-type/employment-app-source-type.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IJobRequisition } from 'app/shared/model/job-requisition.model';
import { JobRequisitionService } from 'app/entities/job-requisition/job-requisition.service';

type SelectableEntity = IEmplPosition | IStatus | IEmploymentAppSourceType | IParty | IJobRequisition;

@Component({
  selector: 'sys-employment-app-update',
  templateUrl: './employment-app-update.component.html',
})
export class EmploymentAppUpdateComponent implements OnInit {
  isSaving = false;
  emplpositions: IEmplPosition[] = [];
  statuses: IStatus[] = [];
  employmentappsourcetypes: IEmploymentAppSourceType[] = [];
  parties: IParty[] = [];
  jobrequisitions: IJobRequisition[] = [];

  editForm = this.fb.group({
    id: [],
    applicationDate: [],
    emplPosition: [],
    status: [],
    source: [],
    applyingParty: [],
    referredByParty: [],
    approverParty: [],
    jobRequisition: [],
  });

  constructor(
    protected employmentAppService: EmploymentAppService,
    protected emplPositionService: EmplPositionService,
    protected statusService: StatusService,
    protected employmentAppSourceTypeService: EmploymentAppSourceTypeService,
    protected partyService: PartyService,
    protected jobRequisitionService: JobRequisitionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employmentApp }) => {
      if (!employmentApp.id) {
        const today = moment().startOf('day');
        employmentApp.applicationDate = today;
      }

      this.updateForm(employmentApp);

      this.emplPositionService.query().subscribe((res: HttpResponse<IEmplPosition[]>) => (this.emplpositions = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));

      this.employmentAppSourceTypeService
        .query()
        .subscribe((res: HttpResponse<IEmploymentAppSourceType[]>) => (this.employmentappsourcetypes = res.body || []));

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.jobRequisitionService.query().subscribe((res: HttpResponse<IJobRequisition[]>) => (this.jobrequisitions = res.body || []));
    });
  }

  updateForm(employmentApp: IEmploymentApp): void {
    this.editForm.patchValue({
      id: employmentApp.id,
      applicationDate: employmentApp.applicationDate ? employmentApp.applicationDate.format(DATE_TIME_FORMAT) : null,
      emplPosition: employmentApp.emplPosition,
      status: employmentApp.status,
      source: employmentApp.source,
      applyingParty: employmentApp.applyingParty,
      referredByParty: employmentApp.referredByParty,
      approverParty: employmentApp.approverParty,
      jobRequisition: employmentApp.jobRequisition,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employmentApp = this.createFromForm();
    if (employmentApp.id !== undefined) {
      this.subscribeToSaveResponse(this.employmentAppService.update(employmentApp));
    } else {
      this.subscribeToSaveResponse(this.employmentAppService.create(employmentApp));
    }
  }

  private createFromForm(): IEmploymentApp {
    return {
      ...new EmploymentApp(),
      id: this.editForm.get(['id'])!.value,
      applicationDate: this.editForm.get(['applicationDate'])!.value
        ? moment(this.editForm.get(['applicationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      emplPosition: this.editForm.get(['emplPosition'])!.value,
      status: this.editForm.get(['status'])!.value,
      source: this.editForm.get(['source'])!.value,
      applyingParty: this.editForm.get(['applyingParty'])!.value,
      referredByParty: this.editForm.get(['referredByParty'])!.value,
      approverParty: this.editForm.get(['approverParty'])!.value,
      jobRequisition: this.editForm.get(['jobRequisition'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmploymentApp>>): void {
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
