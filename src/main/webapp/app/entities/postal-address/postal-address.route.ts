import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPostalAddress, PostalAddress } from 'app/shared/model/postal-address.model';
import { PostalAddressService } from './postal-address.service';
import { PostalAddressComponent } from './postal-address.component';
import { PostalAddressDetailComponent } from './postal-address-detail.component';
import { PostalAddressUpdateComponent } from './postal-address-update.component';

@Injectable({ providedIn: 'root' })
export class PostalAddressResolve implements Resolve<IPostalAddress> {
  constructor(private service: PostalAddressService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPostalAddress> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((postalAddress: HttpResponse<PostalAddress>) => {
          if (postalAddress.body) {
            return of(postalAddress.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PostalAddress());
  }
}

export const postalAddressRoute: Routes = [
  {
    path: '',
    component: PostalAddressComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.postalAddress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PostalAddressDetailComponent,
    resolve: {
      postalAddress: PostalAddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.postalAddress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PostalAddressUpdateComponent,
    resolve: {
      postalAddress: PostalAddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.postalAddress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PostalAddressUpdateComponent,
    resolve: {
      postalAddress: PostalAddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.postalAddress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
