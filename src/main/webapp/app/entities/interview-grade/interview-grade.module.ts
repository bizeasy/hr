import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { InterviewGradeComponent } from './interview-grade.component';
import { InterviewGradeDetailComponent } from './interview-grade-detail.component';
import { InterviewGradeUpdateComponent } from './interview-grade-update.component';
import { InterviewGradeDeleteDialogComponent } from './interview-grade-delete-dialog.component';
import { interviewGradeRoute } from './interview-grade.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(interviewGradeRoute)],
  declarations: [
    InterviewGradeComponent,
    InterviewGradeDetailComponent,
    InterviewGradeUpdateComponent,
    InterviewGradeDeleteDialogComponent,
  ],
  entryComponents: [InterviewGradeDeleteDialogComponent],
})
export class HrInterviewGradeModule {}
