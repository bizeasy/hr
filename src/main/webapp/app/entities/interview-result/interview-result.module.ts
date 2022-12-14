import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { InterviewResultComponent } from './interview-result.component';
import { InterviewResultDetailComponent } from './interview-result-detail.component';
import { InterviewResultUpdateComponent } from './interview-result-update.component';
import { InterviewResultDeleteDialogComponent } from './interview-result-delete-dialog.component';
import { interviewResultRoute } from './interview-result.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(interviewResultRoute)],
  declarations: [
    InterviewResultComponent,
    InterviewResultDetailComponent,
    InterviewResultUpdateComponent,
    InterviewResultDeleteDialogComponent,
  ],
  entryComponents: [InterviewResultDeleteDialogComponent],
})
export class HrInterviewResultModule {}
