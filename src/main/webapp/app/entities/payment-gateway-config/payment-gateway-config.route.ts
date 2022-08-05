import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPaymentGatewayConfig, PaymentGatewayConfig } from 'app/shared/model/payment-gateway-config.model';
import { PaymentGatewayConfigService } from './payment-gateway-config.service';
import { PaymentGatewayConfigComponent } from './payment-gateway-config.component';
import { PaymentGatewayConfigDetailComponent } from './payment-gateway-config-detail.component';
import { PaymentGatewayConfigUpdateComponent } from './payment-gateway-config-update.component';

@Injectable({ providedIn: 'root' })
export class PaymentGatewayConfigResolve implements Resolve<IPaymentGatewayConfig> {
  constructor(private service: PaymentGatewayConfigService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentGatewayConfig> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paymentGatewayConfig: HttpResponse<PaymentGatewayConfig>) => {
          if (paymentGatewayConfig.body) {
            return of(paymentGatewayConfig.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaymentGatewayConfig());
  }
}

export const paymentGatewayConfigRoute: Routes = [
  {
    path: '',
    component: PaymentGatewayConfigComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.paymentGatewayConfig.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentGatewayConfigDetailComponent,
    resolve: {
      paymentGatewayConfig: PaymentGatewayConfigResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.paymentGatewayConfig.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentGatewayConfigUpdateComponent,
    resolve: {
      paymentGatewayConfig: PaymentGatewayConfigResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.paymentGatewayConfig.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentGatewayConfigUpdateComponent,
    resolve: {
      paymentGatewayConfig: PaymentGatewayConfigResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.paymentGatewayConfig.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
