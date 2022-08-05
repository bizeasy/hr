import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { UomTypeComponent } from './uom-type.component';
import { UomTypeDetailComponent } from './uom-type-detail.component';
import { UomTypeUpdateComponent } from './uom-type-update.component';
import { UomTypeDeleteDialogComponent } from './uom-type-delete-dialog.component';
import { uomTypeRoute } from './uom-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(uomTypeRoute)],
  declarations: [UomTypeComponent, UomTypeDetailComponent, UomTypeUpdateComponent, UomTypeDeleteDialogComponent],
  entryComponents: [UomTypeDeleteDialogComponent],
})
export class HrUomTypeModule {}
