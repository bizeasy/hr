import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { AttachmentComponent } from './attachment.component';
import { AttachmentDetailComponent } from './attachment-detail.component';
import { AttachmentUpdateComponent } from './attachment-update.component';
import { AttachmentDeleteDialogComponent } from './attachment-delete-dialog.component';
import { attachmentRoute } from './attachment.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(attachmentRoute)],
  declarations: [AttachmentComponent, AttachmentDetailComponent, AttachmentUpdateComponent, AttachmentDeleteDialogComponent],
  entryComponents: [AttachmentDeleteDialogComponent],
})
export class HrAttachmentModule {}
