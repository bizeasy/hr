import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { UserGroupAuthorityComponent } from './user-group-authority.component';
import { UserGroupAuthorityDetailComponent } from './user-group-authority-detail.component';
import { UserGroupAuthorityUpdateComponent } from './user-group-authority-update.component';
import { UserGroupAuthorityDeleteDialogComponent } from './user-group-authority-delete-dialog.component';
import { userGroupAuthorityRoute } from './user-group-authority.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(userGroupAuthorityRoute)],
  declarations: [
    UserGroupAuthorityComponent,
    UserGroupAuthorityDetailComponent,
    UserGroupAuthorityUpdateComponent,
    UserGroupAuthorityDeleteDialogComponent,
  ],
  entryComponents: [UserGroupAuthorityDeleteDialogComponent],
})
export class HrUserGroupAuthorityModule {}
