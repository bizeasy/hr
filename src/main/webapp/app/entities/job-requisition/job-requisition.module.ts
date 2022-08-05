import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { JobRequisitionComponent } from './job-requisition.component';
import { JobRequisitionDetailComponent } from './job-requisition-detail.component';
import { JobRequisitionUpdateComponent } from './job-requisition-update.component';
import { JobRequisitionDeleteDialogComponent } from './job-requisition-delete-dialog.component';
import { jobRequisitionRoute } from './job-requisition.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(jobRequisitionRoute)],
  declarations: [
    JobRequisitionComponent,
    JobRequisitionDetailComponent,
    JobRequisitionUpdateComponent,
    JobRequisitionDeleteDialogComponent,
  ],
  entryComponents: [JobRequisitionDeleteDialogComponent],
})
export class HrJobRequisitionModule {}
