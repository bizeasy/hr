import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { EmplLeaveTypeComponent } from './empl-leave-type.component';
import { EmplLeaveTypeDetailComponent } from './empl-leave-type-detail.component';
import { EmplLeaveTypeUpdateComponent } from './empl-leave-type-update.component';
import { EmplLeaveTypeDeleteDialogComponent } from './empl-leave-type-delete-dialog.component';
import { emplLeaveTypeRoute } from './empl-leave-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(emplLeaveTypeRoute)],
  declarations: [EmplLeaveTypeComponent, EmplLeaveTypeDetailComponent, EmplLeaveTypeUpdateComponent, EmplLeaveTypeDeleteDialogComponent],
  entryComponents: [EmplLeaveTypeDeleteDialogComponent],
})
export class HrEmplLeaveTypeModule {}
