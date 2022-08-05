import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { EmplPositionTypeRateComponent } from './empl-position-type-rate.component';
import { EmplPositionTypeRateDetailComponent } from './empl-position-type-rate-detail.component';
import { EmplPositionTypeRateUpdateComponent } from './empl-position-type-rate-update.component';
import { EmplPositionTypeRateDeleteDialogComponent } from './empl-position-type-rate-delete-dialog.component';
import { emplPositionTypeRateRoute } from './empl-position-type-rate.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(emplPositionTypeRateRoute)],
  declarations: [
    EmplPositionTypeRateComponent,
    EmplPositionTypeRateDetailComponent,
    EmplPositionTypeRateUpdateComponent,
    EmplPositionTypeRateDeleteDialogComponent,
  ],
  entryComponents: [EmplPositionTypeRateDeleteDialogComponent],
})
export class HrEmplPositionTypeRateModule {}
