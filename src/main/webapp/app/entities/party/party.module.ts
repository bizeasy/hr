import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PartyComponent } from './party.component';
import { PartyDetailComponent } from './party-detail.component';
import { PartyUpdateComponent } from './party-update.component';
import { PartyDeleteDialogComponent } from './party-delete-dialog.component';
import { partyRoute } from './party.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(partyRoute)],
  declarations: [PartyComponent, PartyDetailComponent, PartyUpdateComponent, PartyDeleteDialogComponent],
  entryComponents: [PartyDeleteDialogComponent],
})
export class HrPartyModule {}
