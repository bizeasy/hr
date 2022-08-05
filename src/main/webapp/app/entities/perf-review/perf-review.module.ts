import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PerfReviewComponent } from './perf-review.component';
import { PerfReviewDetailComponent } from './perf-review-detail.component';
import { PerfReviewUpdateComponent } from './perf-review-update.component';
import { PerfReviewDeleteDialogComponent } from './perf-review-delete-dialog.component';
import { perfReviewRoute } from './perf-review.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(perfReviewRoute)],
  declarations: [PerfReviewComponent, PerfReviewDetailComponent, PerfReviewUpdateComponent, PerfReviewDeleteDialogComponent],
  entryComponents: [PerfReviewDeleteDialogComponent],
})
export class HrPerfReviewModule {}
