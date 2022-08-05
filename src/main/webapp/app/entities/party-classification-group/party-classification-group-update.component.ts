import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPartyClassificationGroup, PartyClassificationGroup } from 'app/shared/model/party-classification-group.model';
import { PartyClassificationGroupService } from './party-classification-group.service';
import { IPartyClassificationType } from 'app/shared/model/party-classification-type.model';
import { PartyClassificationTypeService } from 'app/entities/party-classification-type/party-classification-type.service';

@Component({
  selector: 'sys-party-classification-group-update',
  templateUrl: './party-classification-group-update.component.html',
})
export class PartyClassificationGroupUpdateComponent implements OnInit {
  isSaving = false;
  partyclassificationtypes: IPartyClassificationType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(60)]],
    classificationType: [],
  });

  constructor(
    protected partyClassificationGroupService: PartyClassificationGroupService,
    protected partyClassificationTypeService: PartyClassificationTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partyClassificationGroup }) => {
      this.updateForm(partyClassificationGroup);

      this.partyClassificationTypeService
        .query()
        .subscribe((res: HttpResponse<IPartyClassificationType[]>) => (this.partyclassificationtypes = res.body || []));
    });
  }

  updateForm(partyClassificationGroup: IPartyClassificationGroup): void {
    this.editForm.patchValue({
      id: partyClassificationGroup.id,
      name: partyClassificationGroup.name,
      description: partyClassificationGroup.description,
      classificationType: partyClassificationGroup.classificationType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partyClassificationGroup = this.createFromForm();
    if (partyClassificationGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.partyClassificationGroupService.update(partyClassificationGroup));
    } else {
      this.subscribeToSaveResponse(this.partyClassificationGroupService.create(partyClassificationGroup));
    }
  }

  private createFromForm(): IPartyClassificationGroup {
    return {
      ...new PartyClassificationGroup(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      classificationType: this.editForm.get(['classificationType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartyClassificationGroup>>): void {
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

  trackById(index: number, item: IPartyClassificationType): any {
    return item.id;
  }
}
