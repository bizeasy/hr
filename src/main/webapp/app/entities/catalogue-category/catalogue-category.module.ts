import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { CatalogueCategoryComponent } from './catalogue-category.component';
import { CatalogueCategoryDetailComponent } from './catalogue-category-detail.component';
import { CatalogueCategoryUpdateComponent } from './catalogue-category-update.component';
import { CatalogueCategoryDeleteDialogComponent } from './catalogue-category-delete-dialog.component';
import { catalogueCategoryRoute } from './catalogue-category.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(catalogueCategoryRoute)],
  declarations: [
    CatalogueCategoryComponent,
    CatalogueCategoryDetailComponent,
    CatalogueCategoryUpdateComponent,
    CatalogueCategoryDeleteDialogComponent,
  ],
  entryComponents: [CatalogueCategoryDeleteDialogComponent],
})
export class HrCatalogueCategoryModule {}
