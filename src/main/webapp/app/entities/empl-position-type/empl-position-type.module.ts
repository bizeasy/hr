import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { EmplPositionTypeComponent } from './empl-position-type.component';
import { EmplPositionTypeDetailComponent } from './empl-position-type-detail.component';
import { EmplPositionTypeUpdateComponent } from './empl-position-type-update.component';
import { EmplPositionTypeDeleteDialogComponent } from './empl-position-type-delete-dialog.component';
import { emplPositionTypeRoute } from './empl-position-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(emplPositionTypeRoute)],
  declarations: [
    EmplPositionTypeComponent,
    EmplPositionTypeDetailComponent,
    EmplPositionTypeUpdateComponent,
    EmplPositionTypeDeleteDialogComponent,
  ],
  entryComponents: [EmplPositionTypeDeleteDialogComponent],
})
export class HrEmplPositionTypeModule {}
