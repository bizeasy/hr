import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInventoryItemType, InventoryItemType } from 'app/shared/model/inventory-item-type.model';
import { InventoryItemTypeService } from './inventory-item-type.service';
import { InventoryItemTypeComponent } from './inventory-item-type.component';
import { InventoryItemTypeDetailComponent } from './inventory-item-type-detail.component';
import { InventoryItemTypeUpdateComponent } from './inventory-item-type-update.component';

@Injectable({ providedIn: 'root' })
export class InventoryItemTypeResolve implements Resolve<IInventoryItemType> {
  constructor(private service: InventoryItemTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInventoryItemType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((inventoryItemType: HttpResponse<InventoryItemType>) => {
          if (inventoryItemType.body) {
            return of(inventoryItemType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InventoryItemType());
  }
}

export const inventoryItemTypeRoute: Routes = [
  {
    path: '',
    component: InventoryItemTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.inventoryItemType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InventoryItemTypeDetailComponent,
    resolve: {
      inventoryItemType: InventoryItemTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.inventoryItemType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InventoryItemTypeUpdateComponent,
    resolve: {
      inventoryItemType: InventoryItemTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.inventoryItemType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InventoryItemTypeUpdateComponent,
    resolve: {
      inventoryItemType: InventoryItemTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.inventoryItemType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
