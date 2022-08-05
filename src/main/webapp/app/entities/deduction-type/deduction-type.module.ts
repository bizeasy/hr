import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { DeductionTypeComponent } from './deduction-type.component';
import { DeductionTypeDetailComponent } from './deduction-type-detail.component';
import { DeductionTypeUpdateComponent } from './deduction-type-update.component';
import { DeductionTypeDeleteDialogComponent } from './deduction-type-delete-dialog.component';
import { deductionTypeRoute } from './deduction-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(deductionTypeRoute)],
  declarations: [DeductionTypeComponent, DeductionTypeDetailComponent, DeductionTypeUpdateComponent, DeductionTypeDeleteDialogComponent],
  entryComponents: [DeductionTypeDeleteDialogComponent],
})
export class HrDeductionTypeModule {}
