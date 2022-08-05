import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmployment, Employment } from 'app/shared/model/employment.model';
import { EmploymentService } from './employment.service';
import { IReason } from 'app/shared/model/reason.model';
import { ReasonService } from 'app/entities/reason/reason.service';
import { ITerminationType } from 'app/shared/model/termination-type.model';
import { TerminationTypeService } from 'app/entities/termination-type/termination-type.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IRoleType } from 'app/shared/model/role-type.model';
import { RoleTypeService } from 'app/entities/role-type/role-type.service';

type SelectableEntity = IReason | ITerminationType | IParty | IRoleType;

@Component({
  selector: 'sys-employment-update',
  templateUrl: './employment-update.component.html',
})
export class EmploymentUpdateComponent implements OnInit {
  isSaving = false;
  reasons: IReason[] = [];
  terminationtypes: ITerminationType[] = [];
  parties: IParty[] = [];
  roletypes: IRoleType[] = [];
  fromDateDp: any;
  thruDateDp: any;

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    terminationReason: [],
    terminationType: [],
    employee: [],
    fromParty: [],
    roleTypeFrom: [],
    roleTypeTo: [],
  });

  constructor(
    protected employmentService: EmploymentService,
    protected reasonService: ReasonService,
    protected terminationTypeService: TerminationTypeService,
    protected partyService: PartyService,
    protected roleTypeService: RoleTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employment }) => {
      this.updateForm(employment);

      this.reasonService.query().subscribe((res: HttpResponse<IReason[]>) => (this.reasons = res.body || []));

      this.terminationTypeService.query().subscribe((res: HttpResponse<ITerminationType[]>) => (this.terminationtypes = res.body || []));

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.roleTypeService.query().subscribe((res: HttpResponse<IRoleType[]>) => (this.roletypes = res.body || []));
    });
  }

  updateForm(employment: IEmployment): void {
    this.editForm.patchValue({
      id: employment.id,
      fromDate: employment.fromDate,
      thruDate: employment.thruDate,
      terminationReason: employment.terminationReason,
      terminationType: employment.terminationType,
      employee: employment.employee,
      fromParty: employment.fromParty,
      roleTypeFrom: employment.roleTypeFrom,
      roleTypeTo: employment.roleTypeTo,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employment = this.createFromForm();
    if (employment.id !== undefined) {
      this.subscribeToSaveResponse(this.employmentService.update(employment));
    } else {
      this.subscribeToSaveResponse(this.employmentService.create(employment));
    }
  }

  private createFromForm(): IEmployment {
    return {
      ...new Employment(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      thruDate: this.editForm.get(['thruDate'])!.value,
      terminationReason: this.editForm.get(['terminationReason'])!.value,
      terminationType: this.editForm.get(['terminationType'])!.value,
      employee: this.editForm.get(['employee'])!.value,
      fromParty: this.editForm.get(['fromParty'])!.value,
      roleTypeFrom: this.editForm.get(['roleTypeFrom'])!.value,
      roleTypeTo: this.editForm.get(['roleTypeTo'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployment>>): void {
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
