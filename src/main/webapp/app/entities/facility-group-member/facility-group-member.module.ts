import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { FacilityGroupMemberComponent } from './facility-group-member.component';
import { FacilityGroupMemberDetailComponent } from './facility-group-member-detail.component';
import { FacilityGroupMemberUpdateComponent } from './facility-group-member-update.component';
import { FacilityGroupMemberDeleteDialogComponent } from './facility-group-member-delete-dialog.component';
import { facilityGroupMemberRoute } from './facility-group-member.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(facilityGroupMemberRoute)],
  declarations: [
    FacilityGroupMemberComponent,
    FacilityGroupMemberDetailComponent,
    FacilityGroupMemberUpdateComponent,
    FacilityGroupMemberDeleteDialogComponent,
  ],
  entryComponents: [FacilityGroupMemberDeleteDialogComponent],
})
export class HrFacilityGroupMemberModule {}
