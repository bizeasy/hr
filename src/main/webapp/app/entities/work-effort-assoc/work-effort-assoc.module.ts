import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { WorkEffortAssocComponent } from './work-effort-assoc.component';
import { WorkEffortAssocDetailComponent } from './work-effort-assoc-detail.component';
import { WorkEffortAssocUpdateComponent } from './work-effort-assoc-update.component';
import { WorkEffortAssocDeleteDialogComponent } from './work-effort-assoc-delete-dialog.component';
import { workEffortAssocRoute } from './work-effort-assoc.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(workEffortAssocRoute)],
  declarations: [
    WorkEffortAssocComponent,
    WorkEffortAssocDetailComponent,
    WorkEffortAssocUpdateComponent,
    WorkEffortAssocDeleteDialogComponent,
  ],
  entryComponents: [WorkEffortAssocDeleteDialogComponent],
})
export class HrWorkEffortAssocModule {}
