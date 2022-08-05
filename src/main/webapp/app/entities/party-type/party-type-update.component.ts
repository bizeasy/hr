import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPartyType, PartyType } from 'app/shared/model/party-type.model';
import { PartyTypeService } from './party-type.service';

@Component({
  selector: 'sys-party-type-update',
  templateUrl: './party-type-update.component.html',
})
export class PartyTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(60)]],
  });

  constructor(protected partyTypeService: PartyTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyType }) => {
      this.updateForm(partyType);
    });
  }

  updateForm(partyType: IPartyType): void {
    this.editForm.patchValue({
      id: partyType.id,
      name: partyType.name,
      description: partyType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyType = this.createFromForm();
    if (partyType.id !== undefined) {
      this.subscribeToSaveResponse(this.partyTypeService.update(partyType));
    } else {
      this.subscribeToSaveResponse(this.partyTypeService.create(partyType));
    }
  }

  private createFromForm(): IPartyType {
    return {
      ...new PartyType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyType>>): void {
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
