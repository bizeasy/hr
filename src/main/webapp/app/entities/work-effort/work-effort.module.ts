import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { WorkEffortComponent } from './work-effort.component';
import { WorkEffortDetailComponent } from './work-effort-detail.component';
import { WorkEffortUpdateComponent } from './work-effort-update.component';
import { WorkEffortDeleteDialogComponent } from './work-effort-delete-dialog.component';
import { workEffortRoute } from './work-effort.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(workEffortRoute)],
  declarations: [WorkEffortComponent, WorkEffortDetailComponent, WorkEffortUpdateComponent, WorkEffortDeleteDialogComponent],
  entryComponents: [WorkEffortDeleteDialogComponent],
})
export class HrWorkEffortModule {}
