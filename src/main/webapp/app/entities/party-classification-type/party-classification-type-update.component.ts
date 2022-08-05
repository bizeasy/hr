import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPartyClassificationType, PartyClassificationType } from 'app/shared/model/party-classification-type.model';
import { PartyClassificationTypeService } from './party-classification-type.service';

@Component({
  selector: 'sys-party-classification-type-update',
  templateUrl: './party-classification-type-update.component.html',
})
export class PartyClassificationTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(60)]],
  });

  constructor(
    protected partyClassificationTypeService: PartyClassificationTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyClassificationType }) => {
      this.updateForm(partyClassificationType);
    });
  }

  updateForm(partyClassificationType: IPartyClassificationType): void {
    this.editForm.patchValue({
      id: partyClassificationType.id,
      name: partyClassificationType.name,
      description: partyClassificationType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyClassificationType = this.createFromForm();
    if (partyClassificationType.id !== undefined) {
      this.subscribeToSaveResponse(this.partyClassificationTypeService.update(partyClassificationType));
    } else {
      this.subscribeToSaveResponse(this.partyClassificationTypeService.create(partyClassificationType));
    }
  }

  private createFromForm(): IPartyClassificationType {
    return {
      ...new PartyClassificationType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyClassificationType>>): void {
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
