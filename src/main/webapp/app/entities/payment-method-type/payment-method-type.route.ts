import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPaymentMethodType, PaymentMethodType } from 'app/shared/model/payment-method-type.model';
import { PaymentMethodTypeService } from './payment-method-type.service';
import { PaymentMethodTypeComponent } from './payment-method-type.component';
import { PaymentMethodTypeDetailComponent } from './payment-method-type-detail.component';
import { PaymentMethodTypeUpdateComponent } from './payment-method-type-update.component';

@Injectable({ providedIn: 'root' })
export class PaymentMethodTypeResolve implements Resolve<IPaymentMethodType> {
  constructor(private service: PaymentMethodTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentMethodType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paymentMethodType: HttpResponse<PaymentMethodType>) => {
          if (paymentMethodType.body) {
            return of(paymentMethodType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaymentMethodType());
  }
}

export const paymentMethodTypeRoute: Routes = [
  {
    path: '',
    component: PaymentMethodTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.paymentMethodType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentMethodTypeDetailComponent,
    resolve: {
      paymentMethodType: PaymentMethodTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.paymentMethodType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentMethodTypeUpdateComponent,
    resolve: {
      paymentMethodType: PaymentMethodTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.paymentMethodType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentMethodTypeUpdateComponent,
    resolve: {
      paymentMethodType: PaymentMethodTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.paymentMethodType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
