import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEquipmentUsageLog } from 'app/shared/model/equipment-usage-log.model';

@Component({
  selector: 'sys-equipment-usage-log-detail',
  templateUrl: './equipment-usage-log-detail.component.html',
})
export class EquipmentUsageLogDetailComponent implements OnInit {
  equipmentUsageLog: IEquipmentUsageLog | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ equipmentUsageLog }) => (this.equipmentUsageLog = equipmentUsageLog));
  }

  previousState(): void {
    window.history.back();
  }
}
