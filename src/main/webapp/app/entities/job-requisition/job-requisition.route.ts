import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IJobRequisition, JobRequisition } from 'app/shared/model/job-requisition.model';
import { JobRequisitionService } from './job-requisition.service';
import { JobRequisitionComponent } from './job-requisition.component';
import { JobRequisitionDetailComponent } from './job-requisition-detail.component';
import { JobRequisitionUpdateComponent } from './job-requisition-update.component';

@Injectable({ providedIn: 'root' })
export class JobRequisitionResolve implements Resolve<IJobRequisition> {
  constructor(private service: JobRequisitionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJobRequisition> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((jobRequisition: HttpResponse<JobRequisition>) => {
          if (jobRequisition.body) {
            return of(jobRequisition.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new JobRequisition());
  }
}

export const jobRequisitionRoute: Routes = [
  {
    path: '',
    component: JobRequisitionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.jobRequisition.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JobRequisitionDetailComponent,
    resolve: {
      jobRequisition: JobRequisitionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.jobRequisition.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JobRequisitionUpdateComponent,
    resolve: {
      jobRequisition: JobRequisitionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.jobRequisition.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JobRequisitionUpdateComponent,
    resolve: {
      jobRequisition: JobRequisitionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.jobRequisition.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
