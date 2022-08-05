import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFacilityEquipment } from 'app/shared/model/facility-equipment.model';

@Component({
  selector: 'sys-facility-equipment-detail',
  templateUrl: './facility-equipment-detail.component.html',
})
export class FacilityEquipmentDetailComponent implements OnInit {
  facilityEquipment: IFacilityEquipment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facilityEquipment }) => (this.facilityEquipment = facilityEquipment));
  }

  previousState(): void {
    window.history.back();
  }
}
