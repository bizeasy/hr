import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PerfRatingTypeComponent } from './perf-rating-type.component';
import { PerfRatingTypeDetailComponent } from './perf-rating-type-detail.component';
import { PerfRatingTypeUpdateComponent } from './perf-rating-type-update.component';
import { PerfRatingTypeDeleteDialogComponent } from './perf-rating-type-delete-dialog.component';
import { perfRatingTypeRoute } from './perf-rating-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(perfRatingTypeRoute)],
  declarations: [
    PerfRatingTypeComponent,
    PerfRatingTypeDetailComponent,
    PerfRatingTypeUpdateComponent,
    PerfRatingTypeDeleteDialogComponent,
  ],
  entryComponents: [PerfRatingTypeDeleteDialogComponent],
})
export class HrPerfRatingTypeModule {}
