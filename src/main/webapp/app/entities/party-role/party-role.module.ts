import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PartyRoleComponent } from './party-role.component';
import { PartyRoleDetailComponent } from './party-role-detail.component';
import { PartyRoleUpdateComponent } from './party-role-update.component';
import { PartyRoleDeleteDialogComponent } from './party-role-delete-dialog.component';
import { partyRoleRoute } from './party-role.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(partyRoleRoute)],
  declarations: [PartyRoleComponent, PartyRoleDetailComponent, PartyRoleUpdateComponent, PartyRoleDeleteDialogComponent],
  entryComponents: [PartyRoleDeleteDialogComponent],
})
export class HrPartyRoleModule {}
