import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IItemIssuance, ItemIssuance } from 'app/shared/model/item-issuance.model';
import { ItemIssuanceService } from './item-issuance.service';
import { ItemIssuanceComponent } from './item-issuance.component';
import { ItemIssuanceDetailComponent } from './item-issuance-detail.component';
import { ItemIssuanceUpdateComponent } from './item-issuance-update.component';

@Injectable({ providedIn: 'root' })
export class ItemIssuanceResolve implements Resolve<IItemIssuance> {
  constructor(private service: ItemIssuanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItemIssuance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((itemIssuance: HttpResponse<ItemIssuance>) => {
          if (itemIssuance.body) {
            return of(itemIssuance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ItemIssuance());
  }
}

export const itemIssuanceRoute: Routes = [
  {
    path: '',
    component: ItemIssuanceComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.itemIssuance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ItemIssuanceDetailComponent,
    resolve: {
      itemIssuance: ItemIssuanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.itemIssuance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ItemIssuanceUpdateComponent,
    resolve: {
      itemIssuance: ItemIssuanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.itemIssuance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ItemIssuanceUpdateComponent,
    resolve: {
      itemIssuance: ItemIssuanceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.itemIssuance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
