import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInventoryTransfer, InventoryTransfer } from 'app/shared/model/inventory-transfer.model';
import { InventoryTransferService } from './inventory-transfer.service';
import { InventoryTransferComponent } from './inventory-transfer.component';
import { InventoryTransferDetailComponent } from './inventory-transfer-detail.component';
import { InventoryTransferUpdateComponent } from './inventory-transfer-update.component';

@Injectable({ providedIn: 'root' })
export class InventoryTransferResolve implements Resolve<IInventoryTransfer> {
  constructor(private service: InventoryTransferService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInventoryTransfer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((inventoryTransfer: HttpResponse<InventoryTransfer>) => {
          if (inventoryTransfer.body) {
            return of(inventoryTransfer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InventoryTransfer());
  }
}

export const inventoryTransferRoute: Routes = [
  {
    path: '',
    component: InventoryTransferComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.inventoryTransfer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InventoryTransferDetailComponent,
    resolve: {
      inventoryTransfer: InventoryTransferResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.inventoryTransfer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InventoryTransferUpdateComponent,
    resolve: {
      inventoryTransfer: InventoryTransferResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.inventoryTransfer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InventoryTransferUpdateComponent,
    resolve: {
      inventoryTransfer: InventoryTransferResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.inventoryTransfer.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
