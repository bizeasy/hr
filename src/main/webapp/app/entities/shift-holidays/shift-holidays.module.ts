import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ShiftHolidaysComponent } from './shift-holidays.component';
import { ShiftHolidaysDetailComponent } from './shift-holidays-detail.component';
import { ShiftHolidaysUpdateComponent } from './shift-holidays-update.component';
import { ShiftHolidaysDeleteDialogComponent } from './shift-holidays-delete-dialog.component';
import { shiftHolidaysRoute } from './shift-holidays.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(shiftHolidaysRoute)],
  declarations: [ShiftHolidaysComponent, ShiftHolidaysDetailComponent, ShiftHolidaysUpdateComponent, ShiftHolidaysDeleteDialogComponent],
  entryComponents: [ShiftHolidaysDeleteDialogComponent],
})
export class HrShiftHolidaysModule {}
