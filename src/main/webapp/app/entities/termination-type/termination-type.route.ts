import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITerminationType, TerminationType } from 'app/shared/model/termination-type.model';
import { TerminationTypeService } from './termination-type.service';
import { TerminationTypeComponent } from './termination-type.component';
import { TerminationTypeDetailComponent } from './termination-type-detail.component';
import { TerminationTypeUpdateComponent } from './termination-type-update.component';

@Injectable({ providedIn: 'root' })
export class TerminationTypeResolve implements Resolve<ITerminationType> {
  constructor(private service: TerminationTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITerminationType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((terminationType: HttpResponse<TerminationType>) => {
          if (terminationType.body) {
            return of(terminationType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TerminationType());
  }
}

export const terminationTypeRoute: Routes = [
  {
    path: '',
    component: TerminationTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.terminationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TerminationTypeDetailComponent,
    resolve: {
      terminationType: TerminationTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.terminationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TerminationTypeUpdateComponent,
    resolve: {
      terminationType: TerminationTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.terminationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TerminationTypeUpdateComponent,
    resolve: {
      terminationType: TerminationTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.terminationType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
