import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { FacilityPartyComponent } from './facility-party.component';
import { FacilityPartyDetailComponent } from './facility-party-detail.component';
import { FacilityPartyUpdateComponent } from './facility-party-update.component';
import { FacilityPartyDeleteDialogComponent } from './facility-party-delete-dialog.component';
import { facilityPartyRoute } from './facility-party.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(facilityPartyRoute)],
  declarations: [FacilityPartyComponent, FacilityPartyDetailComponent, FacilityPartyUpdateComponent, FacilityPartyDeleteDialogComponent],
  entryComponents: [FacilityPartyDeleteDialogComponent],
})
export class HrFacilityPartyModule {}
