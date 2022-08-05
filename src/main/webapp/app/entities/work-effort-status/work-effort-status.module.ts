import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { WorkEffortStatusComponent } from './work-effort-status.component';
import { WorkEffortStatusDetailComponent } from './work-effort-status-detail.component';
import { WorkEffortStatusUpdateComponent } from './work-effort-status-update.component';
import { WorkEffortStatusDeleteDialogComponent } from './work-effort-status-delete-dialog.component';
import { workEffortStatusRoute } from './work-effort-status.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(workEffortStatusRoute)],
  declarations: [
    WorkEffortStatusComponent,
    WorkEffortStatusDetailComponent,
    WorkEffortStatusUpdateComponent,
    WorkEffortStatusDeleteDialogComponent,
  ],
  entryComponents: [WorkEffortStatusDeleteDialogComponent],
})
export class HrWorkEffortStatusModule {}
