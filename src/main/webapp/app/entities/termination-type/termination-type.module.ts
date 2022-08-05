import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { TerminationTypeComponent } from './termination-type.component';
import { TerminationTypeDetailComponent } from './termination-type-detail.component';
import { TerminationTypeUpdateComponent } from './termination-type-update.component';
import { TerminationTypeDeleteDialogComponent } from './termination-type-delete-dialog.component';
import { terminationTypeRoute } from './termination-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(terminationTypeRoute)],
  declarations: [
    TerminationTypeComponent,
    TerminationTypeDetailComponent,
    TerminationTypeUpdateComponent,
    TerminationTypeDeleteDialogComponent,
  ],
  entryComponents: [TerminationTypeDeleteDialogComponent],
})
export class HrTerminationTypeModule {}
