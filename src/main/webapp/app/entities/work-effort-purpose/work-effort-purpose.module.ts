import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { WorkEffortPurposeComponent } from './work-effort-purpose.component';
import { WorkEffortPurposeDetailComponent } from './work-effort-purpose-detail.component';
import { WorkEffortPurposeUpdateComponent } from './work-effort-purpose-update.component';
import { WorkEffortPurposeDeleteDialogComponent } from './work-effort-purpose-delete-dialog.component';
import { workEffortPurposeRoute } from './work-effort-purpose.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(workEffortPurposeRoute)],
  declarations: [
    WorkEffortPurposeComponent,
    WorkEffortPurposeDetailComponent,
    WorkEffortPurposeUpdateComponent,
    WorkEffortPurposeDeleteDialogComponent,
  ],
  entryComponents: [WorkEffortPurposeDeleteDialogComponent],
})
export class HrWorkEffortPurposeModule {}
