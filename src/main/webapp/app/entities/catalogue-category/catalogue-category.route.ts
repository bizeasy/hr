import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICatalogueCategory, CatalogueCategory } from 'app/shared/model/catalogue-category.model';
import { CatalogueCategoryService } from './catalogue-category.service';
import { CatalogueCategoryComponent } from './catalogue-category.component';
import { CatalogueCategoryDetailComponent } from './catalogue-category-detail.component';
import { CatalogueCategoryUpdateComponent } from './catalogue-category-update.component';

@Injectable({ providedIn: 'root' })
export class CatalogueCategoryResolve implements Resolve<ICatalogueCategory> {
  constructor(private service: CatalogueCategoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICatalogueCategory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((catalogueCategory: HttpResponse<CatalogueCategory>) => {
          if (catalogueCategory.body) {
            return of(catalogueCategory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CatalogueCategory());
  }
}

export const catalogueCategoryRoute: Routes = [
  {
    path: '',
    component: CatalogueCategoryComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.catalogueCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CatalogueCategoryDetailComponent,
    resolve: {
      catalogueCategory: CatalogueCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.catalogueCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CatalogueCategoryUpdateComponent,
    resolve: {
      catalogueCategory: CatalogueCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.catalogueCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CatalogueCategoryUpdateComponent,
    resolve: {
      catalogueCategory: CatalogueCategoryResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.catalogueCategory.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
