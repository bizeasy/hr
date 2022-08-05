import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PerfReviewItemTypeComponent } from './perf-review-item-type.component';
import { PerfReviewItemTypeDetailComponent } from './perf-review-item-type-detail.component';
import { PerfReviewItemTypeUpdateComponent } from './perf-review-item-type-update.component';
import { PerfReviewItemTypeDeleteDialogComponent } from './perf-review-item-type-delete-dialog.component';
import { perfReviewItemTypeRoute } from './perf-review-item-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(perfReviewItemTypeRoute)],
  declarations: [
    PerfReviewItemTypeComponent,
    PerfReviewItemTypeDetailComponent,
    PerfReviewItemTypeUpdateComponent,
    PerfReviewItemTypeDeleteDialogComponent,
  ],
  entryComponents: [PerfReviewItemTypeDeleteDialogComponent],
})
export class HrPerfReviewItemTypeModule {}
