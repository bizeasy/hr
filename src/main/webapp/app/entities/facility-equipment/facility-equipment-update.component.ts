import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFacilityEquipment, FacilityEquipment } from 'app/shared/model/facility-equipment.model';
import { FacilityEquipmentService } from './facility-equipment.service';
import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from 'app/entities/facility/facility.service';
import { IEquipment } from 'app/shared/model/equipment.model';
import { EquipmentService } from 'app/entities/equipment/equipment.service';

type SelectableEntity = IFacility | IEquipment;

@Component({
  selector: 'sys-facility-equipment-update',
  templateUrl: './facility-equipment-update.component.html',
})
export class FacilityEquipmentUpdateComponent implements OnInit {
  isSaving = false;
  facilities: IFacility[] = [];
  equipment: IEquipment[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(60)]],
    facility: [],
    equipment: [],
  });

  constructor(
    protected facilityEquipmentService: FacilityEquipmentService,
    protected facilityService: FacilityService,
    protected equipmentService: EquipmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facilityEquipment }) => {
      this.updateForm(facilityEquipment);

      this.facilityService.query().subscribe((res: HttpResponse<IFacility[]>) => (this.facilities = res.body || []));

      this.equipmentService.query().subscribe((res: HttpResponse<IEquipment[]>) => (this.equipment = res.body || []));
    });
  }

  updateForm(facilityEquipment: IFacilityEquipment): void {
    this.editForm.patchValue({
      id: facilityEquipment.id,
      name: facilityEquipment.name,
      description: facilityEquipment.description,
      facility: facilityEquipment.facility,
      equipment: facilityEquipment.equipment,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facilityEquipment = this.createFromForm();
    if (facilityEquipment.id !== undefined) {
      this.subscribeToSaveResponse(this.facilityEquipmentService.update(facilityEquipment));
    } else {
      this.subscribeToSaveResponse(this.facilityEquipmentService.create(facilityEquipment));
    }
  }

  private createFromForm(): IFacilityEquipment {
    return {
      ...new FacilityEquipment(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      facility: this.editForm.get(['facility'])!.value,
      equipment: this.editForm.get(['equipment'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacilityEquipment>>): void {
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
