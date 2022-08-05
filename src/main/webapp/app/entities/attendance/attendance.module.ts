import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { AttendanceComponent } from './attendance.component';
import { AttendanceDetailComponent } from './attendance-detail.component';
import { AttendanceUpdateComponent } from './attendance-update.component';
import { AttendanceDeleteDialogComponent } from './attendance-delete-dialog.component';
import { attendanceRoute } from './attendance.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(attendanceRoute)],
  declarations: [AttendanceComponent, AttendanceDetailComponent, AttendanceUpdateComponent, AttendanceDeleteDialogComponent],
  entryComponents: [AttendanceDeleteDialogComponent],
})
export class HrAttendanceModule {}
