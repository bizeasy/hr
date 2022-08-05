import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { EmplPositionReportingStructComponent } from './empl-position-reporting-struct.component';
import { EmplPositionReportingStructDetailComponent } from './empl-position-reporting-struct-detail.component';
import { EmplPositionReportingStructUpdateComponent } from './empl-position-reporting-struct-update.component';
import { EmplPositionReportingStructDeleteDialogComponent } from './empl-position-reporting-struct-delete-dialog.component';
import { emplPositionReportingStructRoute } from './empl-position-reporting-struct.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(emplPositionReportingStructRoute)],
  declarations: [
    EmplPositionReportingStructComponent,
    EmplPositionReportingStructDetailComponent,
    EmplPositionReportingStructUpdateComponent,
    EmplPositionReportingStructDeleteDialogComponent,
  ],
  entryComponents: [EmplPositionReportingStructDeleteDialogComponent],
})
export class HrEmplPositionReportingStructModule {}
