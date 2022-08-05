import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmplPositionType, EmplPositionType } from 'app/shared/model/empl-position-type.model';
import { EmplPositionTypeService } from './empl-position-type.service';
import { IEmplPositionGroup } from 'app/shared/model/empl-position-group.model';
import { EmplPositionGroupService } from 'app/entities/empl-position-group/empl-position-group.service';

@Component({
  selector: 'sys-empl-position-type-update',
  templateUrl: './empl-position-type-update.component.html',
})
export class EmplPositionTypeUpdateComponent implements OnInit {
  isSaving = false;
  emplpositiongroups: IEmplPositionGroup[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(60)]],
    group: [],
  });

  constructor(
    protected emplPositionTypeService: EmplPositionTypeService,
    protected emplPositionGroupService: EmplPositionGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emplPositionType }) => {
      this.updateForm(emplPositionType);

      this.emplPositionGroupService
        .query()
        .subscribe((res: HttpResponse<IEmplPositionGroup[]>) => (this.emplpositiongroups = res.body || []));
    });
  }

  updateForm(emplPositionType: IEmplPositionType): void {
    this.editForm.patchValue({
      id: emplPositionType.id,
      name: emplPositionType.name,
      description: emplPositionType.description,
      group: emplPositionType.group,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emplPositionType = this.createFromForm();
    if (emplPositionType.id !== undefined) {
      this.subscribeToSaveResponse(this.emplPositionTypeService.update(emplPositionType));
    } else {
      this.subscribeToSaveResponse(this.emplPositionTypeService.create(emplPositionType));
    }
  }

  private createFromForm(): IEmplPositionType {
    return {
      ...new EmplPositionType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      group: this.editForm.get(['group'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmplPositionType>>): void {
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

  trackById(index: number, item: IEmplPositionGroup): any {
    return item.id;
  }
}
