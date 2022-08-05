import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { EmplPositionComponent } from './empl-position.component';
import { EmplPositionDetailComponent } from './empl-position-detail.component';
import { EmplPositionUpdateComponent } from './empl-position-update.component';
import { EmplPositionDeleteDialogComponent } from './empl-position-delete-dialog.component';
import { emplPositionRoute } from './empl-position.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(emplPositionRoute)],
  declarations: [EmplPositionComponent, EmplPositionDetailComponent, EmplPositionUpdateComponent, EmplPositionDeleteDialogComponent],
  entryComponents: [EmplPositionDeleteDialogComponent],
})
export class HrEmplPositionModule {}
