import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITermType, TermType } from 'app/shared/model/term-type.model';
import { TermTypeService } from './term-type.service';
import { TermTypeComponent } from './term-type.component';
import { TermTypeDetailComponent } from './term-type-detail.component';
import { TermTypeUpdateComponent } from './term-type-update.component';

@Injectable({ providedIn: 'root' })
export class TermTypeResolve implements Resolve<ITermType> {
  constructor(private service: TermTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITermType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((termType: HttpResponse<TermType>) => {
          if (termType.body) {
            return of(termType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TermType());
  }
}

export const termTypeRoute: Routes = [
  {
    path: '',
    component: TermTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.termType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TermTypeDetailComponent,
    resolve: {
      termType: TermTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.termType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TermTypeUpdateComponent,
    resolve: {
      termType: TermTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.termType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TermTypeUpdateComponent,
    resolve: {
      termType: TermTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.termType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
