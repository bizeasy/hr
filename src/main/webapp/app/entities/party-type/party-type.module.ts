import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PartyTypeComponent } from './party-type.component';
import { PartyTypeDetailComponent } from './party-type-detail.component';
import { PartyTypeUpdateComponent } from './party-type-update.component';
import { PartyTypeDeleteDialogComponent } from './party-type-delete-dialog.component';
import { partyTypeRoute } from './party-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(partyTypeRoute)],
  declarations: [PartyTypeComponent, PartyTypeDetailComponent, PartyTypeUpdateComponent, PartyTypeDeleteDialogComponent],
  entryComponents: [PartyTypeDeleteDialogComponent],
})
export class HrPartyTypeModule {}
