import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { FacilityContactMechComponent } from './facility-contact-mech.component';
import { FacilityContactMechDetailComponent } from './facility-contact-mech-detail.component';
import { FacilityContactMechUpdateComponent } from './facility-contact-mech-update.component';
import { FacilityContactMechDeleteDialogComponent } from './facility-contact-mech-delete-dialog.component';
import { facilityContactMechRoute } from './facility-contact-mech.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(facilityContactMechRoute)],
  declarations: [
    FacilityContactMechComponent,
    FacilityContactMechDetailComponent,
    FacilityContactMechUpdateComponent,
    FacilityContactMechDeleteDialogComponent,
  ],
  entryComponents: [FacilityContactMechDeleteDialogComponent],
})
export class HrFacilityContactMechModule {}
