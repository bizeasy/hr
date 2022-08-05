import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PublicHolidaysComponent } from './public-holidays.component';
import { PublicHolidaysDetailComponent } from './public-holidays-detail.component';
import { PublicHolidaysUpdateComponent } from './public-holidays-update.component';
import { PublicHolidaysDeleteDialogComponent } from './public-holidays-delete-dialog.component';
import { publicHolidaysRoute } from './public-holidays.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(publicHolidaysRoute)],
  declarations: [
    PublicHolidaysComponent,
    PublicHolidaysDetailComponent,
    PublicHolidaysUpdateComponent,
    PublicHolidaysDeleteDialogComponent,
  ],
  entryComponents: [PublicHolidaysDeleteDialogComponent],
})
export class HrPublicHolidaysModule {}
