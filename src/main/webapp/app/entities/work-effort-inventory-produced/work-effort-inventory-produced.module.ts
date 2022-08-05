import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { WorkEffortInventoryProducedComponent } from './work-effort-inventory-produced.component';
import { WorkEffortInventoryProducedDetailComponent } from './work-effort-inventory-produced-detail.component';
import { WorkEffortInventoryProducedUpdateComponent } from './work-effort-inventory-produced-update.component';
import { WorkEffortInventoryProducedDeleteDialogComponent } from './work-effort-inventory-produced-delete-dialog.component';
import { workEffortInventoryProducedRoute } from './work-effort-inventory-produced.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(workEffortInventoryProducedRoute)],
  declarations: [
    WorkEffortInventoryProducedComponent,
    WorkEffortInventoryProducedDetailComponent,
    WorkEffortInventoryProducedUpdateComponent,
    WorkEffortInventoryProducedDeleteDialogComponent,
  ],
  entryComponents: [WorkEffortInventoryProducedDeleteDialogComponent],
})
export class HrWorkEffortInventoryProducedModule {}
