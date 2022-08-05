import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { JobInterviewComponent } from './job-interview.component';
import { JobInterviewDetailComponent } from './job-interview-detail.component';
import { JobInterviewUpdateComponent } from './job-interview-update.component';
import { JobInterviewDeleteDialogComponent } from './job-interview-delete-dialog.component';
import { jobInterviewRoute } from './job-interview.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(jobInterviewRoute)],
  declarations: [JobInterviewComponent, JobInterviewDetailComponent, JobInterviewUpdateComponent, JobInterviewDeleteDialogComponent],
  entryComponents: [JobInterviewDeleteDialogComponent],
})
export class HrJobInterviewModule {}
