import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IParty, Party } from 'app/shared/model/party.model';
import { PartyService } from './party.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPartyType } from 'app/shared/model/party-type.model';
import { PartyTypeService } from 'app/entities/party-type/party-type.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';

type SelectableEntity = IUser | IPartyType | IStatus;

@Component({
  selector: 'sys-party-update',
  templateUrl: './party-update.component.html',
})
export class PartyUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  partytypes: IPartyType[] = [];
  statuses: IStatus[] = [];
  birthDateDp: any;

  editForm = this.fb.group({
    id: [],
    isOrganisation: [],
    organisationName: [null, [Validators.maxLength(200)]],
    organisationShortName: [null, [Validators.maxLength(100)]],
    salutation: [],
    firstName: [null, [Validators.maxLength(60)]],
    middleName: [null, [Validators.maxLength(60)]],
    lastName: [null, [Validators.maxLength(60)]],
    gender: [],
    birthDate: [],
    primaryPhone: [null, [Validators.maxLength(20)]],
    primaryEmail: [null, [Validators.minLength(5), Validators.maxLength(75)]],
    isTemporaryPassword: [],
    logoImageUrl: [],
    profileImageUrl: [],
    notes: [null, [Validators.maxLength(255)]],
    birthdate: [],
    dateOfJoining: [],
    trainingCompletedDate: [],
    jdApprovedOn: [],
    employeeId: [],
    authString: [],
    userGroupString: [],
    tanNo: [null, [Validators.maxLength(25)]],
    panNo: [null, [Validators.maxLength(25)]],
    gstNo: [null, [Validators.maxLength(25)]],
    aadharNo: [null, [Validators.maxLength(25)]],
    user: [],
    partyType: [],
    status: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected partyService: PartyService,
    protected userService: UserService,
    protected partyTypeService: PartyTypeService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ party }) => {
      if (!party.id) {
        const today = moment().startOf('day');
        party.birthdate = today;
        party.dateOfJoining = today;
        party.trainingCompletedDate = today;
        party.jdApprovedOn = today;
      }

      this.updateForm(party);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.partyTypeService.query().subscribe((res: HttpResponse<IPartyType[]>) => (this.partytypes = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));
    });
  }

  updateForm(party: IParty): void {
    this.editForm.patchValue({
      id: party.id,
      isOrganisation: party.isOrganisation,
      organisationName: party.organisationName,
      organisationShortName: party.organisationShortName,
      salutation: party.salutation,
      firstName: party.firstName,
      middleName: party.middleName,
      lastName: party.lastName,
      gender: party.gender,
      birthDate: party.birthDate,
      primaryPhone: party.primaryPhone,
      primaryEmail: party.primaryEmail,
      isTemporaryPassword: party.isTemporaryPassword,
      logoImageUrl: party.logoImageUrl,
      profileImageUrl: party.profileImageUrl,
      notes: party.notes,
      birthdate: party.birthdate ? party.birthdate.format(DATE_TIME_FORMAT) : null,
      dateOfJoining: party.dateOfJoining ? party.dateOfJoining.format(DATE_TIME_FORMAT) : null,
      trainingCompletedDate: party.trainingCompletedDate ? party.trainingCompletedDate.format(DATE_TIME_FORMAT) : null,
      jdApprovedOn: party.jdApprovedOn ? party.jdApprovedOn.format(DATE_TIME_FORMAT) : null,
      employeeId: party.employeeId,
      authString: party.authString,
      userGroupString: party.userGroupString,
      tanNo: party.tanNo,
      panNo: party.panNo,
      gstNo: party.gstNo,
      aadharNo: party.aadharNo,
      user: party.user,
      partyType: party.partyType,
      status: party.status,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('hrApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const party = this.createFromForm();
    if (party.id !== undefined) {
      this.subscribeToSaveResponse(this.partyService.update(party));
    } else {
      this.subscribeToSaveResponse(this.partyService.create(party));
    }
  }

  private createFromForm(): IParty {
    return {
      ...new Party(),
      id: this.editForm.get(['id'])!.value,
      isOrganisation: this.editForm.get(['isOrganisation'])!.value,
      organisationName: this.editForm.get(['organisationName'])!.value,
      organisationShortName: this.editForm.get(['organisationShortName'])!.value,
      salutation: this.editForm.get(['salutation'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value,
      primaryPhone: this.editForm.get(['primaryPhone'])!.value,
      primaryEmail: this.editForm.get(['primaryEmail'])!.value,
      isTemporaryPassword: this.editForm.get(['isTemporaryPassword'])!.value,
      logoImageUrl: this.editForm.get(['logoImageUrl'])!.value,
      profileImageUrl: this.editForm.get(['profileImageUrl'])!.value,
      notes: this.editForm.get(['notes'])!.value,
      birthdate: this.editForm.get(['birthdate'])!.value ? moment(this.editForm.get(['birthdate'])!.value, DATE_TIME_FORMAT) : undefined,
      dateOfJoining: this.editForm.get(['dateOfJoining'])!.value
        ? moment(this.editForm.get(['dateOfJoining'])!.value, DATE_TIME_FORMAT)
        : undefined,
      trainingCompletedDate: this.editForm.get(['trainingCompletedDate'])!.value
        ? moment(this.editForm.get(['trainingCompletedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      jdApprovedOn: this.editForm.get(['jdApprovedOn'])!.value
        ? moment(this.editForm.get(['jdApprovedOn'])!.value, DATE_TIME_FORMAT)
        : undefined,
      employeeId: this.editForm.get(['employeeId'])!.value,
      authString: this.editForm.get(['authString'])!.value,
      userGroupString: this.editForm.get(['userGroupString'])!.value,
      tanNo: this.editForm.get(['tanNo'])!.value,
      panNo: this.editForm.get(['panNo'])!.value,
      gstNo: this.editForm.get(['gstNo'])!.value,
      aadharNo: this.editForm.get(['aadharNo'])!.value,
      user: this.editForm.get(['user'])!.value,
      partyType: this.editForm.get(['partyType'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParty>>): void {
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
