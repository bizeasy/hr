import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { EmplLeaveReasonTypeComponent } from './empl-leave-reason-type.component';
import { EmplLeaveReasonTypeDetailComponent } from './empl-leave-reason-type-detail.component';
import { EmplLeaveReasonTypeUpdateComponent } from './empl-leave-reason-type-update.component';
import { EmplLeaveReasonTypeDeleteDialogComponent } from './empl-leave-reason-type-delete-dialog.component';
import { emplLeaveReasonTypeRoute } from './empl-leave-reason-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(emplLeaveReasonTypeRoute)],
  declarations: [
    EmplLeaveReasonTypeComponent,
    EmplLeaveReasonTypeDetailComponent,
    EmplLeaveReasonTypeUpdateComponent,
    EmplLeaveReasonTypeDeleteDialogComponent,
  ],
  entryComponents: [EmplLeaveReasonTypeDeleteDialogComponent],
})
export class HrEmplLeaveReasonTypeModule {}
