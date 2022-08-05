import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPaymentApplication, PaymentApplication } from 'app/shared/model/payment-application.model';
import { PaymentApplicationService } from './payment-application.service';
import { PaymentApplicationComponent } from './payment-application.component';
import { PaymentApplicationDetailComponent } from './payment-application-detail.component';
import { PaymentApplicationUpdateComponent } from './payment-application-update.component';

@Injectable({ providedIn: 'root' })
export class PaymentApplicationResolve implements Resolve<IPaymentApplication> {
  constructor(private service: PaymentApplicationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentApplication> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paymentApplication: HttpResponse<PaymentApplication>) => {
          if (paymentApplication.body) {
            return of(paymentApplication.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaymentApplication());
  }
}

export const paymentApplicationRoute: Routes = [
  {
    path: '',
    component: PaymentApplicationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.paymentApplication.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentApplicationDetailComponent,
    resolve: {
      paymentApplication: PaymentApplicationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.paymentApplication.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentApplicationUpdateComponent,
    resolve: {
      paymentApplication: PaymentApplicationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.paymentApplication.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentApplicationUpdateComponent,
    resolve: {
      paymentApplication: PaymentApplicationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.paymentApplication.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
