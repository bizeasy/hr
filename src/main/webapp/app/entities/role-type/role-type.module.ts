import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { RoleTypeComponent } from './role-type.component';
import { RoleTypeDetailComponent } from './role-type-detail.component';
import { RoleTypeUpdateComponent } from './role-type-update.component';
import { RoleTypeDeleteDialogComponent } from './role-type-delete-dialog.component';
import { roleTypeRoute } from './role-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(roleTypeRoute)],
  declarations: [RoleTypeComponent, RoleTypeDetailComponent, RoleTypeUpdateComponent, RoleTypeDeleteDialogComponent],
  entryComponents: [RoleTypeDeleteDialogComponent],
})
export class HrRoleTypeModule {}
