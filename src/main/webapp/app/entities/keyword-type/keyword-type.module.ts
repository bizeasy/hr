import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { KeywordTypeComponent } from './keyword-type.component';
import { KeywordTypeDetailComponent } from './keyword-type-detail.component';
import { KeywordTypeUpdateComponent } from './keyword-type-update.component';
import { KeywordTypeDeleteDialogComponent } from './keyword-type-delete-dialog.component';
import { keywordTypeRoute } from './keyword-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(keywordTypeRoute)],
  declarations: [KeywordTypeComponent, KeywordTypeDetailComponent, KeywordTypeUpdateComponent, KeywordTypeDeleteDialogComponent],
  entryComponents: [KeywordTypeDeleteDialogComponent],
})
export class HrKeywordTypeModule {}
