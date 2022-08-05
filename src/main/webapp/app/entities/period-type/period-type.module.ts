import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PeriodTypeComponent } from './period-type.component';
import { PeriodTypeDetailComponent } from './period-type-detail.component';
import { PeriodTypeUpdateComponent } from './period-type-update.component';
import { PeriodTypeDeleteDialogComponent } from './period-type-delete-dialog.component';
import { periodTypeRoute } from './period-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(periodTypeRoute)],
  declarations: [PeriodTypeComponent, PeriodTypeDetailComponent, PeriodTypeUpdateComponent, PeriodTypeDeleteDialogComponent],
  entryComponents: [PeriodTypeDeleteDialogComponent],
})
export class HrPeriodTypeModule {}
