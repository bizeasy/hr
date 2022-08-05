import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { WorkEffortAssocTypeComponent } from './work-effort-assoc-type.component';
import { WorkEffortAssocTypeDetailComponent } from './work-effort-assoc-type-detail.component';
import { WorkEffortAssocTypeUpdateComponent } from './work-effort-assoc-type-update.component';
import { WorkEffortAssocTypeDeleteDialogComponent } from './work-effort-assoc-type-delete-dialog.component';
import { workEffortAssocTypeRoute } from './work-effort-assoc-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(workEffortAssocTypeRoute)],
  declarations: [
    WorkEffortAssocTypeComponent,
    WorkEffortAssocTypeDetailComponent,
    WorkEffortAssocTypeUpdateComponent,
    WorkEffortAssocTypeDeleteDialogComponent,
  ],
  entryComponents: [WorkEffortAssocTypeDeleteDialogComponent],
})
export class HrWorkEffortAssocTypeModule {}
