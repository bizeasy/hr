import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPartyContactMech, PartyContactMech } from 'app/shared/model/party-contact-mech.model';
import { PartyContactMechService } from './party-contact-mech.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';
import { IContactMech } from 'app/shared/model/contact-mech.model';
import { ContactMechService } from 'app/entities/contact-mech/contact-mech.service';
import { IContactMechPurpose } from 'app/shared/model/contact-mech-purpose.model';
import { ContactMechPurposeService } from 'app/entities/contact-mech-purpose/contact-mech-purpose.service';

type SelectableEntity = IParty | IContactMech | IContactMechPurpose;

@Component({
  selector: 'sys-party-contact-mech-update',
  templateUrl: './party-contact-mech-update.component.html',
})
export class PartyContactMechUpdateComponent implements OnInit {
  isSaving = false;
  parties: IParty[] = [];
  contactmeches: IContactMech[] = [];
  contactmechpurposes: IContactMechPurpose[] = [];

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    party: [],
    contactMech: [],
    contactMechPurpose: [],
  });

  constructor(
    protected partyContactMechService: PartyContactMechService,
    protected partyService: PartyService,
    protected contactMechService: ContactMechService,
    protected contactMechPurposeService: ContactMechPurposeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyContactMech }) => {
      if (!partyContactMech.id) {
        const today = moment().startOf('day');
        partyContactMech.fromDate = today;
        partyContactMech.thruDate = today;
      }

      this.updateForm(partyContactMech);

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));

      this.contactMechService.query().subscribe((res: HttpResponse<IContactMech[]>) => (this.contactmeches = res.body || []));

      this.contactMechPurposeService
        .query()
        .subscribe((res: HttpResponse<IContactMechPurpose[]>) => (this.contactmechpurposes = res.body || []));
    });
  }

  updateForm(partyContactMech: IPartyContactMech): void {
    this.editForm.patchValue({
      id: partyContactMech.id,
      fromDate: partyContactMech.fromDate ? partyContactMech.fromDate.format(DATE_TIME_FORMAT) : null,
      thruDate: partyContactMech.thruDate ? partyContactMech.thruDate.format(DATE_TIME_FORMAT) : null,
      party: partyContactMech.party,
      contactMech: partyContactMech.contactMech,
      contactMechPurpose: partyContactMech.contactMechPurpose,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyContactMech = this.createFromForm();
    if (partyContactMech.id !== undefined) {
      this.subscribeToSaveResponse(this.partyContactMechService.update(partyContactMech));
    } else {
      this.subscribeToSaveResponse(this.partyContactMechService.create(partyContactMech));
    }
  }

  private createFromForm(): IPartyContactMech {
    return {
      ...new PartyContactMech(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      thruDate: this.editForm.get(['thruDate'])!.value ? moment(this.editForm.get(['thruDate'])!.value, DATE_TIME_FORMAT) : undefined,
      party: this.editForm.get(['party'])!.value,
      contactMech: this.editForm.get(['contactMech'])!.value,
      contactMechPurpose: this.editForm.get(['contactMechPurpose'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyContactMech>>): void {
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
