import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { EquipmentUsageLogComponent } from './equipment-usage-log.component';
import { EquipmentUsageLogDetailComponent } from './equipment-usage-log-detail.component';
import { EquipmentUsageLogUpdateComponent } from './equipment-usage-log-update.component';
import { EquipmentUsageLogDeleteDialogComponent } from './equipment-usage-log-delete-dialog.component';
import { equipmentUsageLogRoute } from './equipment-usage-log.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(equipmentUsageLogRoute)],
  declarations: [
    EquipmentUsageLogComponent,
    EquipmentUsageLogDetailComponent,
    EquipmentUsageLogUpdateComponent,
    EquipmentUsageLogDeleteDialogComponent,
  ],
  entryComponents: [EquipmentUsageLogDeleteDialogComponent],
})
export class HrEquipmentUsageLogModule {}
