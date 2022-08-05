import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { JobInterviewTypeComponent } from './job-interview-type.component';
import { JobInterviewTypeDetailComponent } from './job-interview-type-detail.component';
import { JobInterviewTypeUpdateComponent } from './job-interview-type-update.component';
import { JobInterviewTypeDeleteDialogComponent } from './job-interview-type-delete-dialog.component';
import { jobInterviewTypeRoute } from './job-interview-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(jobInterviewTypeRoute)],
  declarations: [
    JobInterviewTypeComponent,
    JobInterviewTypeDetailComponent,
    JobInterviewTypeUpdateComponent,
    JobInterviewTypeDeleteDialogComponent,
  ],
  entryComponents: [JobInterviewTypeDeleteDialogComponent],
})
export class HrJobInterviewTypeModule {}
