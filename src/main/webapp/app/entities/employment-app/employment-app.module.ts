import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { EmploymentAppComponent } from './employment-app.component';
import { EmploymentAppDetailComponent } from './employment-app-detail.component';
import { EmploymentAppUpdateComponent } from './employment-app-update.component';
import { EmploymentAppDeleteDialogComponent } from './employment-app-delete-dialog.component';
import { employmentAppRoute } from './employment-app.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(employmentAppRoute)],
  declarations: [EmploymentAppComponent, EmploymentAppDetailComponent, EmploymentAppUpdateComponent, EmploymentAppDeleteDialogComponent],
  entryComponents: [EmploymentAppDeleteDialogComponent],
})
export class HrEmploymentAppModule {}
