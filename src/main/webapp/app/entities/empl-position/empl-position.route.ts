import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmplPosition, EmplPosition } from 'app/shared/model/empl-position.model';
import { EmplPositionService } from './empl-position.service';
import { EmplPositionComponent } from './empl-position.component';
import { EmplPositionDetailComponent } from './empl-position-detail.component';
import { EmplPositionUpdateComponent } from './empl-position-update.component';

@Injectable({ providedIn: 'root' })
export class EmplPositionResolve implements Resolve<IEmplPosition> {
  constructor(private service: EmplPositionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmplPosition> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((emplPosition: HttpResponse<EmplPosition>) => {
          if (emplPosition.body) {
            return of(emplPosition.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmplPosition());
  }
}

export const emplPositionRoute: Routes = [
  {
    path: '',
    component: EmplPositionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPosition.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmplPositionDetailComponent,
    resolve: {
      emplPosition: EmplPositionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPosition.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmplPositionUpdateComponent,
    resolve: {
      emplPosition: EmplPositionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPosition.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmplPositionUpdateComponent,
    resolve: {
      emplPosition: EmplPositionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPosition.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
