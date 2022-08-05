import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEquipmentType, EquipmentType } from 'app/shared/model/equipment-type.model';
import { EquipmentTypeService } from './equipment-type.service';

@Component({
  selector: 'sys-equipment-type-update',
  templateUrl: './equipment-type-update.component.html',
})
export class EquipmentTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(60)]],
  });

  constructor(protected equipmentTypeService: EquipmentTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ equipmentType }) => {
      this.updateForm(equipmentType);
    });
  }

  updateForm(equipmentType: IEquipmentType): void {
    this.editForm.patchValue({
      id: equipmentType.id,
      name: equipmentType.name,
      description: equipmentType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const equipmentType = this.createFromForm();
    if (equipmentType.id !== undefined) {
      this.subscribeToSaveResponse(this.equipmentTypeService.update(equipmentType));
    } else {
      this.subscribeToSaveResponse(this.equipmentTypeService.create(equipmentType));
    }
  }

  private createFromForm(): IEquipmentType {
    return {
      ...new EquipmentType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipmentType>>): void {
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
