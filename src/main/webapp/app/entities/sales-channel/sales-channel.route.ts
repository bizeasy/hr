import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISalesChannel, SalesChannel } from 'app/shared/model/sales-channel.model';
import { SalesChannelService } from './sales-channel.service';
import { SalesChannelComponent } from './sales-channel.component';
import { SalesChannelDetailComponent } from './sales-channel-detail.component';
import { SalesChannelUpdateComponent } from './sales-channel-update.component';

@Injectable({ providedIn: 'root' })
export class SalesChannelResolve implements Resolve<ISalesChannel> {
  constructor(private service: SalesChannelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISalesChannel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((salesChannel: HttpResponse<SalesChannel>) => {
          if (salesChannel.body) {
            return of(salesChannel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SalesChannel());
  }
}

export const salesChannelRoute: Routes = [
  {
    path: '',
    component: SalesChannelComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.salesChannel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SalesChannelDetailComponent,
    resolve: {
      salesChannel: SalesChannelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.salesChannel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SalesChannelUpdateComponent,
    resolve: {
      salesChannel: SalesChannelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.salesChannel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SalesChannelUpdateComponent,
    resolve: {
      salesChannel: SalesChannelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.salesChannel.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
