import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { EmplPositionGroupComponent } from './empl-position-group.component';
import { EmplPositionGroupDetailComponent } from './empl-position-group-detail.component';
import { EmplPositionGroupUpdateComponent } from './empl-position-group-update.component';
import { EmplPositionGroupDeleteDialogComponent } from './empl-position-group-delete-dialog.component';
import { emplPositionGroupRoute } from './empl-position-group.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(emplPositionGroupRoute)],
  declarations: [
    EmplPositionGroupComponent,
    EmplPositionGroupDetailComponent,
    EmplPositionGroupUpdateComponent,
    EmplPositionGroupDeleteDialogComponent,
  ],
  entryComponents: [EmplPositionGroupDeleteDialogComponent],
})
export class HrEmplPositionGroupModule {}
