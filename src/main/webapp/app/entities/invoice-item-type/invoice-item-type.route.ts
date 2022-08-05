import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInvoiceItemType, InvoiceItemType } from 'app/shared/model/invoice-item-type.model';
import { InvoiceItemTypeService } from './invoice-item-type.service';
import { InvoiceItemTypeComponent } from './invoice-item-type.component';
import { InvoiceItemTypeDetailComponent } from './invoice-item-type-detail.component';
import { InvoiceItemTypeUpdateComponent } from './invoice-item-type-update.component';

@Injectable({ providedIn: 'root' })
export class InvoiceItemTypeResolve implements Resolve<IInvoiceItemType> {
  constructor(private service: InvoiceItemTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInvoiceItemType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((invoiceItemType: HttpResponse<InvoiceItemType>) => {
          if (invoiceItemType.body) {
            return of(invoiceItemType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InvoiceItemType());
  }
}

export const invoiceItemTypeRoute: Routes = [
  {
    path: '',
    component: InvoiceItemTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.invoiceItemType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InvoiceItemTypeDetailComponent,
    resolve: {
      invoiceItemType: InvoiceItemTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.invoiceItemType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InvoiceItemTypeUpdateComponent,
    resolve: {
      invoiceItemType: InvoiceItemTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.invoiceItemType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InvoiceItemTypeUpdateComponent,
    resolve: {
      invoiceItemType: InvoiceItemTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.invoiceItemType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
