import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmplPositionGroup, EmplPositionGroup } from 'app/shared/model/empl-position-group.model';
import { EmplPositionGroupService } from './empl-position-group.service';

@Component({
  selector: 'sys-empl-position-group-update',
  templateUrl: './empl-position-group-update.component.html',
})
export class EmplPositionGroupUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(60)]],
  });

  constructor(
    protected emplPositionGroupService: EmplPositionGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplPositionGroup }) => {
      this.updateForm(emplPositionGroup);
    });
  }

  updateForm(emplPositionGroup: IEmplPositionGroup): void {
    this.editForm.patchValue({
      id: emplPositionGroup.id,
      name: emplPositionGroup.name,
      description: emplPositionGroup.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emplPositionGroup = this.createFromForm();
    if (emplPositionGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.emplPositionGroupService.update(emplPositionGroup));
    } else {
      this.subscribeToSaveResponse(this.emplPositionGroupService.create(emplPositionGroup));
    }
  }

  private createFromForm(): IEmplPositionGroup {
    return {
      ...new EmplPositionGroup(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmplPositionGroup>>): void {
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
