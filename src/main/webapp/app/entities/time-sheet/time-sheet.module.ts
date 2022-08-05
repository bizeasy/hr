import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { TimeSheetComponent } from './time-sheet.component';
import { TimeSheetDetailComponent } from './time-sheet-detail.component';
import { TimeSheetUpdateComponent } from './time-sheet-update.component';
import { TimeSheetDeleteDialogComponent } from './time-sheet-delete-dialog.component';
import { timeSheetRoute } from './time-sheet.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(timeSheetRoute)],
  declarations: [TimeSheetComponent, TimeSheetDetailComponent, TimeSheetUpdateComponent, TimeSheetDeleteDialogComponent],
  entryComponents: [TimeSheetDeleteDialogComponent],
})
export class HrTimeSheetModule {}
