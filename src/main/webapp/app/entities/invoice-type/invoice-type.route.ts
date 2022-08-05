import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInvoiceType, InvoiceType } from 'app/shared/model/invoice-type.model';
import { InvoiceTypeService } from './invoice-type.service';
import { InvoiceTypeComponent } from './invoice-type.component';
import { InvoiceTypeDetailComponent } from './invoice-type-detail.component';
import { InvoiceTypeUpdateComponent } from './invoice-type-update.component';

@Injectable({ providedIn: 'root' })
export class InvoiceTypeResolve implements Resolve<IInvoiceType> {
  constructor(private service: InvoiceTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInvoiceType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((invoiceType: HttpResponse<InvoiceType>) => {
          if (invoiceType.body) {
            return of(invoiceType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InvoiceType());
  }
}

export const invoiceTypeRoute: Routes = [
  {
    path: '',
    component: InvoiceTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.invoiceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InvoiceTypeDetailComponent,
    resolve: {
      invoiceType: InvoiceTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.invoiceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InvoiceTypeUpdateComponent,
    resolve: {
      invoiceType: InvoiceTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.invoiceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InvoiceTypeUpdateComponent,
    resolve: {
      invoiceType: InvoiceTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.invoiceType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
