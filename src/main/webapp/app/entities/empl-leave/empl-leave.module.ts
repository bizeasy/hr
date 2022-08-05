import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { EmplLeaveComponent } from './empl-leave.component';
import { EmplLeaveDetailComponent } from './empl-leave-detail.component';
import { EmplLeaveUpdateComponent } from './empl-leave-update.component';
import { EmplLeaveDeleteDialogComponent } from './empl-leave-delete-dialog.component';
import { emplLeaveRoute } from './empl-leave.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(emplLeaveRoute)],
  declarations: [EmplLeaveComponent, EmplLeaveDetailComponent, EmplLeaveUpdateComponent, EmplLeaveDeleteDialogComponent],
  entryComponents: [EmplLeaveDeleteDialogComponent],
})
export class HrEmplLeaveModule {}
