import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ShiftWeekendsComponent } from './shift-weekends.component';
import { ShiftWeekendsDetailComponent } from './shift-weekends-detail.component';
import { ShiftWeekendsUpdateComponent } from './shift-weekends-update.component';
import { ShiftWeekendsDeleteDialogComponent } from './shift-weekends-delete-dialog.component';
import { shiftWeekendsRoute } from './shift-weekends.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(shiftWeekendsRoute)],
  declarations: [ShiftWeekendsComponent, ShiftWeekendsDetailComponent, ShiftWeekendsUpdateComponent, ShiftWeekendsDeleteDialogComponent],
  entryComponents: [ShiftWeekendsDeleteDialogComponent],
})
export class HrShiftWeekendsModule {}
