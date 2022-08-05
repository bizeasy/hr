import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ReasonTypeComponent } from './reason-type.component';
import { ReasonTypeDetailComponent } from './reason-type-detail.component';
import { ReasonTypeUpdateComponent } from './reason-type-update.component';
import { ReasonTypeDeleteDialogComponent } from './reason-type-delete-dialog.component';
import { reasonTypeRoute } from './reason-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(reasonTypeRoute)],
  declarations: [ReasonTypeComponent, ReasonTypeDetailComponent, ReasonTypeUpdateComponent, ReasonTypeDeleteDialogComponent],
  entryComponents: [ReasonTypeDeleteDialogComponent],
})
export class HrReasonTypeModule {}
