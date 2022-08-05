import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ContentTypeComponent } from './content-type.component';
import { ContentTypeDetailComponent } from './content-type-detail.component';
import { ContentTypeUpdateComponent } from './content-type-update.component';
import { ContentTypeDeleteDialogComponent } from './content-type-delete-dialog.component';
import { contentTypeRoute } from './content-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(contentTypeRoute)],
  declarations: [ContentTypeComponent, ContentTypeDetailComponent, ContentTypeUpdateComponent, ContentTypeDeleteDialogComponent],
  entryComponents: [ContentTypeDeleteDialogComponent],
})
export class HrContentTypeModule {}
