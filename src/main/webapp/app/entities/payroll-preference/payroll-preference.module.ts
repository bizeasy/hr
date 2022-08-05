import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PayrollPreferenceComponent } from './payroll-preference.component';
import { PayrollPreferenceDetailComponent } from './payroll-preference-detail.component';
import { PayrollPreferenceUpdateComponent } from './payroll-preference-update.component';
import { PayrollPreferenceDeleteDialogComponent } from './payroll-preference-delete-dialog.component';
import { payrollPreferenceRoute } from './payroll-preference.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(payrollPreferenceRoute)],
  declarations: [
    PayrollPreferenceComponent,
    PayrollPreferenceDetailComponent,
    PayrollPreferenceUpdateComponent,
    PayrollPreferenceDeleteDialogComponent,
  ],
  entryComponents: [PayrollPreferenceDeleteDialogComponent],
})
export class HrPayrollPreferenceModule {}
