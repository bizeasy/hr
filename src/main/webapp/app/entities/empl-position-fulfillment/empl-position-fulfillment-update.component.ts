import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmplPositionFulfillment, EmplPositionFulfillment } from 'app/shared/model/empl-position-fulfillment.model';
import { EmplPositionFulfillmentService } from './empl-position-fulfillment.service';
import { IEmplPosition } from 'app/shared/model/empl-position.model';
import { EmplPositionService } from 'app/entities/empl-position/empl-position.service';
import { IParty } from 'app/shared/model/party.model';
import { PartyService } from 'app/entities/party/party.service';

type SelectableEntity = IEmplPosition | IParty;

@Component({
  selector: 'sys-empl-position-fulfillment-update',
  templateUrl: './empl-position-fulfillment-update.component.html',
})
export class EmplPositionFulfillmentUpdateComponent implements OnInit {
  isSaving = false;
  emplpositions: IEmplPosition[] = [];
  parties: IParty[] = [];
  fromDateDp: any;
  thruDateDp: any;

  editForm = this.fb.group({
    id: [],
    fromDate: [],
    thruDate: [],
    comments: [],
    emplPosition: [],
    party: [],
    reportingTo: [],
    managedBy: [],
  });

  constructor(
    protected emplPositionFulfillmentService: EmplPositionFulfillmentService,
    protected emplPositionService: EmplPositionService,
    protected partyService: PartyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplPositionFulfillment }) => {
      this.updateForm(emplPositionFulfillment);

      this.emplPositionService.query().subscribe((res: HttpResponse<IEmplPosition[]>) => (this.emplpositions = res.body || []));

      this.partyService.query().subscribe((res: HttpResponse<IParty[]>) => (this.parties = res.body || []));
    });
  }

  updateForm(emplPositionFulfillment: IEmplPositionFulfillment): void {
    this.editForm.patchValue({
      id: emplPositionFulfillment.id,
      fromDate: emplPositionFulfillment.fromDate,
      thruDate: emplPositionFulfillment.thruDate,
      comments: emplPositionFulfillment.comments,
      emplPosition: emplPositionFulfillment.emplPosition,
      party: emplPositionFulfillment.party,
      reportingTo: emplPositionFulfillment.reportingTo,
      managedBy: emplPositionFulfillment.managedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emplPositionFulfillment = this.createFromForm();
    if (emplPositionFulfillment.id !== undefined) {
      this.subscribeToSaveResponse(this.emplPositionFulfillmentService.update(emplPositionFulfillment));
    } else {
      this.subscribeToSaveResponse(this.emplPositionFulfillmentService.create(emplPositionFulfillment));
    }
  }

  private createFromForm(): IEmplPositionFulfillment {
    return {
      ...new EmplPositionFulfillment(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      thruDate: this.editForm.get(['thruDate'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      emplPosition: this.editForm.get(['emplPosition'])!.value,
      party: this.editForm.get(['party'])!.value,
      reportingTo: this.editForm.get(['reportingTo'])!.value,
      managedBy: this.editForm.get(['managedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmplPositionFulfillment>>): void {
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
