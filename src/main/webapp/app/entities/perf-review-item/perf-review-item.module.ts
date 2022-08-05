import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PerfReviewItemComponent } from './perf-review-item.component';
import { PerfReviewItemDetailComponent } from './perf-review-item-detail.component';
import { PerfReviewItemUpdateComponent } from './perf-review-item-update.component';
import { PerfReviewItemDeleteDialogComponent } from './perf-review-item-delete-dialog.component';
import { perfReviewItemRoute } from './perf-review-item.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(perfReviewItemRoute)],
  declarations: [
    PerfReviewItemComponent,
    PerfReviewItemDetailComponent,
    PerfReviewItemUpdateComponent,
    PerfReviewItemDeleteDialogComponent,
  ],
  entryComponents: [PerfReviewItemDeleteDialogComponent],
})
export class HrPerfReviewItemModule {}
