import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ItemIssuanceComponent } from './item-issuance.component';
import { ItemIssuanceDetailComponent } from './item-issuance-detail.component';
import { ItemIssuanceUpdateComponent } from './item-issuance-update.component';
import { ItemIssuanceDeleteDialogComponent } from './item-issuance-delete-dialog.component';
import { itemIssuanceRoute } from './item-issuance.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(itemIssuanceRoute)],
  declarations: [ItemIssuanceComponent, ItemIssuanceDetailComponent, ItemIssuanceUpdateComponent, ItemIssuanceDeleteDialogComponent],
  entryComponents: [ItemIssuanceDeleteDialogComponent],
})
export class HrItemIssuanceModule {}
