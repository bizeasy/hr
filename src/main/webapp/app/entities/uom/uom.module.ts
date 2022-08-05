import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { UomComponent } from './uom.component';
import { UomDetailComponent } from './uom-detail.component';
import { UomUpdateComponent } from './uom-update.component';
import { UomDeleteDialogComponent } from './uom-delete-dialog.component';
import { uomRoute } from './uom.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(uomRoute)],
  declarations: [UomComponent, UomDetailComponent, UomUpdateComponent, UomDeleteDialogComponent],
  entryComponents: [UomDeleteDialogComponent],
})
export class HrUomModule {}
