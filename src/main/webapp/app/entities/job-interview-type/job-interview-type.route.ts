import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJobInterviewType, JobInterviewType } from 'app/shared/model/job-interview-type.model';
import { JobInterviewTypeService } from './job-interview-type.service';
import { JobInterviewTypeComponent } from './job-interview-type.component';
import { JobInterviewTypeDetailComponent } from './job-interview-type-detail.component';
import { JobInterviewTypeUpdateComponent } from './job-interview-type-update.component';

@Injectable({ providedIn: 'root' })
export class JobInterviewTypeResolve implements Resolve<IJobInterviewType> {
  constructor(private service: JobInterviewTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJobInterviewType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jobInterviewType: HttpResponse<JobInterviewType>) => {
          if (jobInterviewType.body) {
            return of(jobInterviewType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JobInterviewType());
  }
}

export const jobInterviewTypeRoute: Routes = [
  {
    path: '',
    component: JobInterviewTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.jobInterviewType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JobInterviewTypeDetailComponent,
    resolve: {
      jobInterviewType: JobInterviewTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.jobInterviewType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JobInterviewTypeUpdateComponent,
    resolve: {
      jobInterviewType: JobInterviewTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.jobInterviewType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JobInterviewTypeUpdateComponent,
    resolve: {
      jobInterviewType: JobInterviewTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.jobInterviewType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
