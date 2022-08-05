import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJobInterview, JobInterview } from 'app/shared/model/job-interview.model';
import { JobInterviewService } from './job-interview.service';
import { JobInterviewComponent } from './job-interview.component';
import { JobInterviewDetailComponent } from './job-interview-detail.component';
import { JobInterviewUpdateComponent } from './job-interview-update.component';

@Injectable({ providedIn: 'root' })
export class JobInterviewResolve implements Resolve<IJobInterview> {
  constructor(private service: JobInterviewService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJobInterview> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jobInterview: HttpResponse<JobInterview>) => {
          if (jobInterview.body) {
            return of(jobInterview.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JobInterview());
  }
}

export const jobInterviewRoute: Routes = [
  {
    path: '',
    component: JobInterviewComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.jobInterview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JobInterviewDetailComponent,
    resolve: {
      jobInterview: JobInterviewResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.jobInterview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JobInterviewUpdateComponent,
    resolve: {
      jobInterview: JobInterviewResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.jobInterview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JobInterviewUpdateComponent,
    resolve: {
      jobInterview: JobInterviewResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.jobInterview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
