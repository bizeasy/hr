import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { UserGroupMemberComponent } from './user-group-member.component';
import { UserGroupMemberDetailComponent } from './user-group-member-detail.component';
import { UserGroupMemberUpdateComponent } from './user-group-member-update.component';
import { UserGroupMemberDeleteDialogComponent } from './user-group-member-delete-dialog.component';
import { userGroupMemberRoute } from './user-group-member.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(userGroupMemberRoute)],
  declarations: [
    UserGroupMemberComponent,
    UserGroupMemberDetailComponent,
    UserGroupMemberUpdateComponent,
    UserGroupMemberDeleteDialogComponent,
  ],
  entryComponents: [UserGroupMemberDeleteDialogComponent],
})
export class HrUserGroupMemberModule {}
