import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PermissionAuthorityComponent } from './permission-authority.component';
import { PermissionAuthorityDetailComponent } from './permission-authority-detail.component';
import { PermissionAuthorityUpdateComponent } from './permission-authority-update.component';
import { PermissionAuthorityDeleteDialogComponent } from './permission-authority-delete-dialog.component';
import { permissionAuthorityRoute } from './permission-authority.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(permissionAuthorityRoute)],
  declarations: [
    PermissionAuthorityComponent,
    PermissionAuthorityDetailComponent,
    PermissionAuthorityUpdateComponent,
    PermissionAuthorityDeleteDialogComponent,
  ],
  entryComponents: [PermissionAuthorityDeleteDialogComponent],
})
export class HrPermissionAuthorityModule {}
