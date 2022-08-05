import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { FacilityTypeComponent } from './facility-type.component';
import { FacilityTypeDetailComponent } from './facility-type-detail.component';
import { FacilityTypeUpdateComponent } from './facility-type-update.component';
import { FacilityTypeDeleteDialogComponent } from './facility-type-delete-dialog.component';
import { facilityTypeRoute } from './facility-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(facilityTypeRoute)],
  declarations: [FacilityTypeComponent, FacilityTypeDetailComponent, FacilityTypeUpdateComponent, FacilityTypeDeleteDialogComponent],
  entryComponents: [FacilityTypeDeleteDialogComponent],
})
export class HrFacilityTypeModule {}
