import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { StatusValidChangeComponent } from './status-valid-change.component';
import { StatusValidChangeDetailComponent } from './status-valid-change-detail.component';
import { StatusValidChangeUpdateComponent } from './status-valid-change-update.component';
import { StatusValidChangeDeleteDialogComponent } from './status-valid-change-delete-dialog.component';
import { statusValidChangeRoute } from './status-valid-change.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(statusValidChangeRoute)],
  declarations: [
    StatusValidChangeComponent,
    StatusValidChangeDetailComponent,
    StatusValidChangeUpdateComponent,
    StatusValidChangeDeleteDialogComponent,
  ],
  entryComponents: [StatusValidChangeDeleteDialogComponent],
})
export class HrStatusValidChangeModule {}
