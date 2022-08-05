import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInterviewGrade, InterviewGrade } from 'app/shared/model/interview-grade.model';
import { InterviewGradeService } from './interview-grade.service';
import { InterviewGradeComponent } from './interview-grade.component';
import { InterviewGradeDetailComponent } from './interview-grade-detail.component';
import { InterviewGradeUpdateComponent } from './interview-grade-update.component';

@Injectable({ providedIn: 'root' })
export class InterviewGradeResolve implements Resolve<IInterviewGrade> {
  constructor(private service: InterviewGradeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInterviewGrade> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((interviewGrade: HttpResponse<InterviewGrade>) => {
          if (interviewGrade.body) {
            return of(interviewGrade.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InterviewGrade());
  }
}

export const interviewGradeRoute: Routes = [
  {
    path: '',
    component: InterviewGradeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.interviewGrade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InterviewGradeDetailComponent,
    resolve: {
      interviewGrade: InterviewGradeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.interviewGrade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InterviewGradeUpdateComponent,
    resolve: {
      interviewGrade: InterviewGradeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.interviewGrade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InterviewGradeUpdateComponent,
    resolve: {
      interviewGrade: InterviewGradeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.interviewGrade.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
