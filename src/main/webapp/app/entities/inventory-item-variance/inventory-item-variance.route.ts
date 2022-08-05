import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInventoryItemVariance, InventoryItemVariance } from 'app/shared/model/inventory-item-variance.model';
import { InventoryItemVarianceService } from './inventory-item-variance.service';
import { InventoryItemVarianceComponent } from './inventory-item-variance.component';
import { InventoryItemVarianceDetailComponent } from './inventory-item-variance-detail.component';
import { InventoryItemVarianceUpdateComponent } from './inventory-item-variance-update.component';

@Injectable({ providedIn: 'root' })
export class InventoryItemVarianceResolve implements Resolve<IInventoryItemVariance> {
  constructor(private service: InventoryItemVarianceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInventoryItemVariance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((inventoryItemVariance: HttpResponse<InventoryItemVariance>) => {
          if (inventoryItemVariance.body) {
            return of(inventoryItemVariance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InventoryItemVariance());
  }
}

export const inventoryItemVarianceRoute: Routes = [
  {
    path: '',
    component: InventoryItemVarianceComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.inventoryItemVariance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InventoryItemVarianceDetailComponent,
    resolve: {
      inventoryItemVariance: InventoryItemVarianceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.inventoryItemVariance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InventoryItemVarianceUpdateComponent,
    resolve: {
      inventoryItemVariance: InventoryItemVarianceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.inventoryItemVariance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InventoryItemVarianceUpdateComponent,
    resolve: {
      inventoryItemVariance: InventoryItemVarianceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.inventoryItemVariance.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
