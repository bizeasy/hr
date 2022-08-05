import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISkillType, SkillType } from 'app/shared/model/skill-type.model';
import { SkillTypeService } from './skill-type.service';

@Component({
  selector: 'sys-skill-type-update',
  templateUrl: './skill-type-update.component.html',
})
export class SkillTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [],
  });

  constructor(protected skillTypeService: SkillTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ skillType }) => {
      this.updateForm(skillType);
    });
  }

  updateForm(skillType: ISkillType): void {
    this.editForm.patchValue({
      id: skillType.id,
      name: skillType.name,
      description: skillType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const skillType = this.createFromForm();
    if (skillType.id !== undefined) {
      this.subscribeToSaveResponse(this.skillTypeService.update(skillType));
    } else {
      this.subscribeToSaveResponse(this.skillTypeService.create(skillType));
    }
  }

  private createFromForm(): ISkillType {
    return {
      ...new SkillType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISkillType>>): void {
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
