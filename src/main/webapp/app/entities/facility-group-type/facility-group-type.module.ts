import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { FacilityGroupTypeComponent } from './facility-group-type.component';
import { FacilityGroupTypeDetailComponent } from './facility-group-type-detail.component';
import { FacilityGroupTypeUpdateComponent } from './facility-group-type-update.component';
import { FacilityGroupTypeDeleteDialogComponent } from './facility-group-type-delete-dialog.component';
import { facilityGroupTypeRoute } from './facility-group-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(facilityGroupTypeRoute)],
  declarations: [
    FacilityGroupTypeComponent,
    FacilityGroupTypeDetailComponent,
    FacilityGroupTypeUpdateComponent,
    FacilityGroupTypeDeleteDialogComponent,
  ],
  entryComponents: [FacilityGroupTypeDeleteDialogComponent],
})
export class HrFacilityGroupTypeModule {}
