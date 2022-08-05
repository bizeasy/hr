import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { CustomTimePeriodComponent } from './custom-time-period.component';
import { CustomTimePeriodDetailComponent } from './custom-time-period-detail.component';
import { CustomTimePeriodUpdateComponent } from './custom-time-period-update.component';
import { CustomTimePeriodDeleteDialogComponent } from './custom-time-period-delete-dialog.component';
import { customTimePeriodRoute } from './custom-time-period.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(customTimePeriodRoute)],
  declarations: [
    CustomTimePeriodComponent,
    CustomTimePeriodDetailComponent,
    CustomTimePeriodUpdateComponent,
    CustomTimePeriodDeleteDialogComponent,
  ],
  entryComponents: [CustomTimePeriodDeleteDialogComponent],
})
export class HrCustomTimePeriodModule {}
