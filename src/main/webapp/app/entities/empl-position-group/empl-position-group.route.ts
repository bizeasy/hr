import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmplPositionGroup, EmplPositionGroup } from 'app/shared/model/empl-position-group.model';
import { EmplPositionGroupService } from './empl-position-group.service';
import { EmplPositionGroupComponent } from './empl-position-group.component';
import { EmplPositionGroupDetailComponent } from './empl-position-group-detail.component';
import { EmplPositionGroupUpdateComponent } from './empl-position-group-update.component';

@Injectable({ providedIn: 'root' })
export class EmplPositionGroupResolve implements Resolve<IEmplPositionGroup> {
  constructor(private service: EmplPositionGroupService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmplPositionGroup> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((emplPositionGroup: HttpResponse<EmplPositionGroup>) => {
          if (emplPositionGroup.body) {
            return of(emplPositionGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmplPositionGroup());
  }
}

export const emplPositionGroupRoute: Routes = [
  {
    path: '',
    component: EmplPositionGroupComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmplPositionGroupDetailComponent,
    resolve: {
      emplPositionGroup: EmplPositionGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmplPositionGroupUpdateComponent,
    resolve: {
      emplPositionGroup: EmplPositionGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmplPositionGroupUpdateComponent,
    resolve: {
      emplPositionGroup: EmplPositionGroupResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.emplPositionGroup.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
