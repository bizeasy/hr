import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { WorkEffortProductComponent } from './work-effort-product.component';
import { WorkEffortProductDetailComponent } from './work-effort-product-detail.component';
import { WorkEffortProductUpdateComponent } from './work-effort-product-update.component';
import { WorkEffortProductDeleteDialogComponent } from './work-effort-product-delete-dialog.component';
import { workEffortProductRoute } from './work-effort-product.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(workEffortProductRoute)],
  declarations: [
    WorkEffortProductComponent,
    WorkEffortProductDetailComponent,
    WorkEffortProductUpdateComponent,
    WorkEffortProductDeleteDialogComponent,
  ],
  entryComponents: [WorkEffortProductDeleteDialogComponent],
})
export class HrWorkEffortProductModule {}
