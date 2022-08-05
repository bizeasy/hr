import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { FacilityEquipmentComponent } from './facility-equipment.component';
import { FacilityEquipmentDetailComponent } from './facility-equipment-detail.component';
import { FacilityEquipmentUpdateComponent } from './facility-equipment-update.component';
import { FacilityEquipmentDeleteDialogComponent } from './facility-equipment-delete-dialog.component';
import { facilityEquipmentRoute } from './facility-equipment.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(facilityEquipmentRoute)],
  declarations: [
    FacilityEquipmentComponent,
    FacilityEquipmentDetailComponent,
    FacilityEquipmentUpdateComponent,
    FacilityEquipmentDeleteDialogComponent,
  ],
  entryComponents: [FacilityEquipmentDeleteDialogComponent],
})
export class HrFacilityEquipmentModule {}
