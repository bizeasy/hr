import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { WorkEffortInventoryAssignComponent } from './work-effort-inventory-assign.component';
import { WorkEffortInventoryAssignDetailComponent } from './work-effort-inventory-assign-detail.component';
import { WorkEffortInventoryAssignUpdateComponent } from './work-effort-inventory-assign-update.component';
import { WorkEffortInventoryAssignDeleteDialogComponent } from './work-effort-inventory-assign-delete-dialog.component';
import { workEffortInventoryAssignRoute } from './work-effort-inventory-assign.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(workEffortInventoryAssignRoute)],
  declarations: [
    WorkEffortInventoryAssignComponent,
    WorkEffortInventoryAssignDetailComponent,
    WorkEffortInventoryAssignUpdateComponent,
    WorkEffortInventoryAssignDeleteDialogComponent,
  ],
  entryComponents: [WorkEffortInventoryAssignDeleteDialogComponent],
})
export class HrWorkEffortInventoryAssignModule {}
