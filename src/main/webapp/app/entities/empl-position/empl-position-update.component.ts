import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmplPosition, EmplPosition } from 'app/shared/model/empl-position.model';
import { EmplPositionService } from './empl-position.service';
import { IEmplPositionType } from 'app/shared/model/empl-position-type.model';
import { EmplPositionTypeService } from 'app/entities/empl-position-type/empl-position-type.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';

type SelectableEntity = IEmplPositionType | IParty | IStatus;

@Component({
  selector: 'sys-empl-position-update',
  templateUrl: './empl-position-update.component.html',
})
export class EmplPositionUpdateComponent implements OnInit {
  isSaving = false;
  emplpositiontypes: IEmplPositionType[] = [];
  parties: IParty[] = [];
  statuses: IStatus[] = [];
  estimatedFromDateDp: any;
  estimatedThruDateDp: any;
  actualFromDateDp: any;
  actualThruDateDp: any;

  editForm = this.fb.group({
    id: [],
    maxBudget: [],
    minBudget: [],
    estimatedFromDate: [],
    estimatedThruDate: [],
    paidJob: [],
    isFulltime: [],
    isTemporary: [],
    actualFromDate: [],
    actualThruDate: [],
    type: [],
    party: [],
    status: [],
  });

  constructor(
    protected emplPositionService: EmplPositionService,
    protected emplPositionTypeService: EmplPositionTypeService,
    protected partyService: PartyService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplPosition }) => {
      this.updateForm(emplPosition);

      this.emplPositionTypeService.query().subscribe((res: HttpResponse<IEmplPositionType[]>) => (this.emplpositiontypes = res.body || []));

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));
    });
  }

  updateForm(emplPosition: IEmplPosition): void {
    this.editForm.patchValue({
      id: emplPosition.id,
      maxBudget: emplPosition.maxBudget,
      minBudget: emplPosition.minBudget,
      estimatedFromDate: emplPosition.estimatedFromDate,
      estimatedThruDate: emplPosition.estimatedThruDate,
      paidJob: emplPosition.paidJob,
      isFulltime: emplPosition.isFulltime,
      isTemporary: emplPosition.isTemporary,
      actualFromDate: emplPosition.actualFromDate,
      actualThruDate: emplPosition.actualThruDate,
      type: emplPosition.type,
      party: emplPosition.party,
      status: emplPosition.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emplPosition = this.createFromForm();
    if (emplPosition.id !== undefined) {
      this.subscribeToSaveResponse(this.emplPositionService.update(emplPosition));
    } else {
      this.subscribeToSaveResponse(this.emplPositionService.create(emplPosition));
    }
  }

  private createFromForm(): IEmplPosition {
    return {
      ...new EmplPosition(),
      id: this.editForm.get(['id'])!.value,
      maxBudget: this.editForm.get(['maxBudget'])!.value,
      minBudget: this.editForm.get(['minBudget'])!.value,
      estimatedFromDate: this.editForm.get(['estimatedFromDate'])!.value,
      estimatedThruDate: this.editForm.get(['estimatedThruDate'])!.value,
      paidJob: this.editForm.get(['paidJob'])!.value,
      isFulltime: this.editForm.get(['isFulltime'])!.value,
      isTemporary: this.editForm.get(['isTemporary'])!.value,
      actualFromDate: this.editForm.get(['actualFromDate'])!.value,
      actualThruDate: this.editForm.get(['actualThruDate'])!.value,
      type: this.editForm.get(['type'])!.value,
      party: this.editForm.get(['party'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmplPosition>>): void {
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
