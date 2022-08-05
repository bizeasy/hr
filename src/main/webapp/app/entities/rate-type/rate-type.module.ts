import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { RateTypeComponent } from './rate-type.component';
import { RateTypeDetailComponent } from './rate-type-detail.component';
import { RateTypeUpdateComponent } from './rate-type-update.component';
import { RateTypeDeleteDialogComponent } from './rate-type-delete-dialog.component';
import { rateTypeRoute } from './rate-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(rateTypeRoute)],
  declarations: [RateTypeComponent, RateTypeDetailComponent, RateTypeUpdateComponent, RateTypeDeleteDialogComponent],
  entryComponents: [RateTypeDeleteDialogComponent],
})
export class HrRateTypeModule {}
