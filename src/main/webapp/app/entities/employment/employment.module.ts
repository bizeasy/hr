import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { EmploymentComponent } from './employment.component';
import { EmploymentDetailComponent } from './employment-detail.component';
import { EmploymentUpdateComponent } from './employment-update.component';
import { EmploymentDeleteDialogComponent } from './employment-delete-dialog.component';
import { employmentRoute } from './employment.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(employmentRoute)],
  declarations: [EmploymentComponent, EmploymentDetailComponent, EmploymentUpdateComponent, EmploymentDeleteDialogComponent],
  entryComponents: [EmploymentDeleteDialogComponent],
})
export class HrEmploymentModule {}
