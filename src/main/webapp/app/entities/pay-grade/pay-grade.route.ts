import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPayGrade, PayGrade } from 'app/shared/model/pay-grade.model';
import { PayGradeService } from './pay-grade.service';
import { PayGradeComponent } from './pay-grade.component';
import { PayGradeDetailComponent } from './pay-grade-detail.component';
import { PayGradeUpdateComponent } from './pay-grade-update.component';

@Injectable({ providedIn: 'root' })
export class PayGradeResolve implements Resolve<IPayGrade> {
  constructor(private service: PayGradeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPayGrade> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((payGrade: HttpResponse<PayGrade>) => {
          if (payGrade.body) {
            return of(payGrade.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PayGrade());
  }
}

export const payGradeRoute: Routes = [
  {
    path: '',
    component: PayGradeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.payGrade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PayGradeDetailComponent,
    resolve: {
      payGrade: PayGradeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.payGrade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PayGradeUpdateComponent,
    resolve: {
      payGrade: PayGradeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.payGrade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PayGradeUpdateComponent,
    resolve: {
      payGrade: PayGradeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.payGrade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
