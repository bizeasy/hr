import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { SalesChannelComponent } from './sales-channel.component';
import { SalesChannelDetailComponent } from './sales-channel-detail.component';
import { SalesChannelUpdateComponent } from './sales-channel-update.component';
import { SalesChannelDeleteDialogComponent } from './sales-channel-delete-dialog.component';
import { salesChannelRoute } from './sales-channel.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(salesChannelRoute)],
  declarations: [SalesChannelComponent, SalesChannelDetailComponent, SalesChannelUpdateComponent, SalesChannelDeleteDialogComponent],
  entryComponents: [SalesChannelDeleteDialogComponent],
})
export class HrSalesChannelModule {}
