import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { CatalogueCategoryTypeComponent } from './catalogue-category-type.component';
import { CatalogueCategoryTypeDetailComponent } from './catalogue-category-type-detail.component';
import { CatalogueCategoryTypeUpdateComponent } from './catalogue-category-type-update.component';
import { CatalogueCategoryTypeDeleteDialogComponent } from './catalogue-category-type-delete-dialog.component';
import { catalogueCategoryTypeRoute } from './catalogue-category-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(catalogueCategoryTypeRoute)],
  declarations: [
    CatalogueCategoryTypeComponent,
    CatalogueCategoryTypeDetailComponent,
    CatalogueCategoryTypeUpdateComponent,
    CatalogueCategoryTypeDeleteDialogComponent,
  ],
  entryComponents: [CatalogueCategoryTypeDeleteDialogComponent],
})
export class HrCatalogueCategoryTypeModule {}
