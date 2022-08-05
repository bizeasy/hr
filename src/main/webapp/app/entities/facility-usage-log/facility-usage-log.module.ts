import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { FacilityUsageLogComponent } from './facility-usage-log.component';
import { FacilityUsageLogDetailComponent } from './facility-usage-log-detail.component';
import { FacilityUsageLogUpdateComponent } from './facility-usage-log-update.component';
import { FacilityUsageLogDeleteDialogComponent } from './facility-usage-log-delete-dialog.component';
import { facilityUsageLogRoute } from './facility-usage-log.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(facilityUsageLogRoute)],
  declarations: [
    FacilityUsageLogComponent,
    FacilityUsageLogDetailComponent,
    FacilityUsageLogUpdateComponent,
    FacilityUsageLogDeleteDialogComponent,
  ],
  entryComponents: [FacilityUsageLogDeleteDialogComponent],
})
export class HrFacilityUsageLogModule {}
