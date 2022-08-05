import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPartyClassification, PartyClassification } from 'app/shared/model/party-classification.model';
import { PartyClassificationService } from './party-classification.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IPartyClassificationGroup } from 'app/shared/model/party-classification-group.model';
import { PartyClassificationGroupService } from 'app/entities/party-classification-group/party-classification-group.service';

type SelectableEntity = IParty | IPartyClassificationGroup;

@Component({
  selector: 'sys-party-classification-update',
  templateUrl: './party-classification-update.component.html',
})
export class PartyClassificationUpdateComponent implements OnInit {
  isSaving = false;
  parties: IParty[] = [];
  partyclassificationgroups: IPartyClassificationGroup[] = [];

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    party: [],
    classificationGroup: [],
  });

  constructor(
    protected partyClassificationService: PartyClassificationService,
    protected partyService: PartyService,
    protected partyClassificationGroupService: PartyClassificationGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyClassification }) => {
      if (!partyClassification.id) {
        const today = moment().startOf('day');
        partyClassification.fromDate = today;
        partyClassification.thruDate = today;
      }

      this.updateForm(partyClassification);

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.partyClassificationGroupService
        .query()
        .subscribe((res: HttpResponse<IPartyClassificationGroup[]>) => (this.partyclassificationgroups = res.body || []));
    });
  }

  updateForm(partyClassification: IPartyClassification): void {
    this.editForm.patchValue({
      id: partyClassification.id,
      fromDate: partyClassification.fromDate ? partyClassification.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: partyClassification.thruDate ? partyClassification.thruDate.format(DATE_TIME_FORMAT) : null,
      party: partyClassification.party,
      classificationGroup: partyClassification.classificationGroup,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyClassification = this.createFromForm();
    if (partyClassification.id !== undefined) {
      this.subscribeToSaveResponse(this.partyClassificationService.update(partyClassification));
    } else {
      this.subscribeToSaveResponse(this.partyClassificationService.create(partyClassification));
    }
  }

  private createFromForm(): IPartyClassification {
    return {
      ...new PartyClassification(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? moment(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      party: this.editForm.get(['party'])!.value,
      classificationGroup: this.editForm.get(['classificationGroup'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyClassification>>): void {
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
