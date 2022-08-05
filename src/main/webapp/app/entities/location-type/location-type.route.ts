import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILocationType, LocationType } from 'app/shared/model/location-type.model';
import { LocationTypeService } from './location-type.service';
import { LocationTypeComponent } from './location-type.component';
import { LocationTypeDetailComponent } from './location-type-detail.component';
import { LocationTypeUpdateComponent } from './location-type-update.component';

@Injectable({ providedIn: 'root' })
export class LocationTypeResolve implements Resolve<ILocationType> {
  constructor(private service: LocationTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILocationType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((locationType: HttpResponse<LocationType>) => {
          if (locationType.body) {
            return of(locationType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LocationType());
  }
}

export const locationTypeRoute: Routes = [
  {
    path: '',
    component: LocationTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.locationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LocationTypeDetailComponent,
    resolve: {
      locationType: LocationTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.locationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LocationTypeUpdateComponent,
    resolve: {
      locationType: LocationTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.locationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LocationTypeUpdateComponent,
    resolve: {
      locationType: LocationTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.locationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
