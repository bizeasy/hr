import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductCategoryMember, ProductCategoryMember } from 'app/shared/model/product-category-member.model';
import { ProductCategoryMemberService } from './product-category-member.service';
import { ProductCategoryMemberComponent } from './product-category-member.component';
import { ProductCategoryMemberDetailComponent } from './product-category-member-detail.component';
import { ProductCategoryMemberUpdateComponent } from './product-category-member-update.component';

@Injectable({ providedIn: 'root' })
export class ProductCategoryMemberResolve implements Resolve<IProductCategoryMember> {
  constructor(private service: ProductCategoryMemberService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductCategoryMember> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productCategoryMember: HttpResponse<ProductCategoryMember>) => {
          if (productCategoryMember.body) {
            return of(productCategoryMember.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductCategoryMember());
  }
}

export const productCategoryMemberRoute: Routes = [
  {
    path: '',
    component: ProductCategoryMemberComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.productCategoryMember.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductCategoryMemberDetailComponent,
    resolve: {
      productCategoryMember: ProductCategoryMemberResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productCategoryMember.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductCategoryMemberUpdateComponent,
    resolve: {
      productCategoryMember: ProductCategoryMemberResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productCategoryMember.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductCategoryMemberUpdateComponent,
    resolve: {
      productCategoryMember: ProductCategoryMemberResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.productCategoryMember.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
