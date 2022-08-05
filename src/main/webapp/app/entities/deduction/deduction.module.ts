import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { DeductionComponent } from './deduction.component';
import { DeductionDetailComponent } from './deduction-detail.component';
import { DeductionUpdateComponent } from './deduction-update.component';
import { DeductionDeleteDialogComponent } from './deduction-delete-dialog.component';
import { deductionRoute } from './deduction.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(deductionRoute)],
  declarations: [DeductionComponent, DeductionDetailComponent, DeductionUpdateComponent, DeductionDeleteDialogComponent],
  entryComponents: [DeductionDeleteDialogComponent],
})
export class HrDeductionModule {}
