import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITerm, Term } from 'app/shared/model/term.model';
import { TermService } from './term.service';
import { TermComponent } from './term.component';
import { TermDetailComponent } from './term-detail.component';
import { TermUpdateComponent } from './term-update.component';

@Injectable({ providedIn: 'root' })
export class TermResolve implements Resolve<ITerm> {
  constructor(private service: TermService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITerm> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((term: HttpResponse<Term>) => {
          if (term.body) {
            return of(term.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Term());
  }
}

export const termRoute: Routes = [
  {
    path: '',
    component: TermComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.term.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TermDetailComponent,
    resolve: {
      term: TermResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.term.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TermUpdateComponent,
    resolve: {
      term: TermResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.term.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TermUpdateComponent,
    resolve: {
      term: TermResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.term.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
