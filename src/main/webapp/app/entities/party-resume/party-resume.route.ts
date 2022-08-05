import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartyResume, PartyResume } from 'app/shared/model/party-resume.model';
import { PartyResumeService } from './party-resume.service';
import { PartyResumeComponent } from './party-resume.component';
import { PartyResumeDetailComponent } from './party-resume-detail.component';
import { PartyResumeUpdateComponent } from './party-resume-update.component';

@Injectable({ providedIn: 'root' })
export class PartyResumeResolve implements Resolve<IPartyResume> {
  constructor(private service: PartyResumeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyResume> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partyResume: HttpResponse<PartyResume>) => {
          if (partyResume.body) {
            return of(partyResume.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyResume());
  }
}

export const partyResumeRoute: Routes = [
  {
    path: '',
    component: PartyResumeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyResume.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyResumeDetailComponent,
    resolve: {
      partyResume: PartyResumeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyResume.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyResumeUpdateComponent,
    resolve: {
      partyResume: PartyResumeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyResume.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyResumeUpdateComponent,
    resolve: {
      partyResume: PartyResumeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.partyResume.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
