import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { EmploymentAppSourceTypeComponent } from './employment-app-source-type.component';
import { EmploymentAppSourceTypeDetailComponent } from './employment-app-source-type-detail.component';
import { EmploymentAppSourceTypeUpdateComponent } from './employment-app-source-type-update.component';
import { EmploymentAppSourceTypeDeleteDialogComponent } from './employment-app-source-type-delete-dialog.component';
import { employmentAppSourceTypeRoute } from './employment-app-source-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(employmentAppSourceTypeRoute)],
  declarations: [
    EmploymentAppSourceTypeComponent,
    EmploymentAppSourceTypeDetailComponent,
    EmploymentAppSourceTypeUpdateComponent,
    EmploymentAppSourceTypeDeleteDialogComponent,
  ],
  entryComponents: [EmploymentAppSourceTypeDeleteDialogComponent],
})
export class HrEmploymentAppSourceTypeModule {}
