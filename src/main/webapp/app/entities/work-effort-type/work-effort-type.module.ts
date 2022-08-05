import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { WorkEffortTypeComponent } from './work-effort-type.component';
import { WorkEffortTypeDetailComponent } from './work-effort-type-detail.component';
import { WorkEffortTypeUpdateComponent } from './work-effort-type-update.component';
import { WorkEffortTypeDeleteDialogComponent } from './work-effort-type-delete-dialog.component';
import { workEffortTypeRoute } from './work-effort-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(workEffortTypeRoute)],
  declarations: [
    WorkEffortTypeComponent,
    WorkEffortTypeDetailComponent,
    WorkEffortTypeUpdateComponent,
    WorkEffortTypeDeleteDialogComponent,
  ],
  entryComponents: [WorkEffortTypeDeleteDialogComponent],
})
export class HrWorkEffortTypeModule {}
