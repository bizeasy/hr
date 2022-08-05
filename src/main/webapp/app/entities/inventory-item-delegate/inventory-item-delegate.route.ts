import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInventoryItemDelegate, InventoryItemDelegate } from 'app/shared/model/inventory-item-delegate.model';
import { InventoryItemDelegateService } from './inventory-item-delegate.service';
import { InventoryItemDelegateComponent } from './inventory-item-delegate.component';
import { InventoryItemDelegateDetailComponent } from './inventory-item-delegate-detail.component';
import { InventoryItemDelegateUpdateComponent } from './inventory-item-delegate-update.component';

@Injectable({ providedIn: 'root' })
export class InventoryItemDelegateResolve implements Resolve<IInventoryItemDelegate> {
  constructor(private service: InventoryItemDelegateService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInventoryItemDelegate> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((inventoryItemDelegate: HttpResponse<InventoryItemDelegate>) => {
          if (inventoryItemDelegate.body) {
            return of(inventoryItemDelegate.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InventoryItemDelegate());
  }
}

export const inventoryItemDelegateRoute: Routes = [
  {
    path: '',
    component: InventoryItemDelegateComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.inventoryItemDelegate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InventoryItemDelegateDetailComponent,
    resolve: {
      inventoryItemDelegate: InventoryItemDelegateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.inventoryItemDelegate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InventoryItemDelegateUpdateComponent,
    resolve: {
      inventoryItemDelegate: InventoryItemDelegateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.inventoryItemDelegate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InventoryItemDelegateUpdateComponent,
    resolve: {
      inventoryItemDelegate: InventoryItemDelegateResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.inventoryItemDelegate.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
