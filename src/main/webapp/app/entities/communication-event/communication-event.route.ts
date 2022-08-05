import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommunicationEvent, CommunicationEvent } from 'app/shared/model/communication-event.model';
import { CommunicationEventService } from './communication-event.service';
import { CommunicationEventComponent } from './communication-event.component';
import { CommunicationEventDetailComponent } from './communication-event-detail.component';
import { CommunicationEventUpdateComponent } from './communication-event-update.component';

@Injectable({ providedIn: 'root' })
export class CommunicationEventResolve implements Resolve<ICommunicationEvent> {
  constructor(private service: CommunicationEventService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommunicationEvent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((communicationEvent: HttpResponse<CommunicationEvent>) => {
          if (communicationEvent.body) {
            return of(communicationEvent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommunicationEvent());
  }
}

export const communicationEventRoute: Routes = [
  {
    path: '',
    component: CommunicationEventComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.communicationEvent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommunicationEventDetailComponent,
    resolve: {
      communicationEvent: CommunicationEventResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.communicationEvent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommunicationEventUpdateComponent,
    resolve: {
      communicationEvent: CommunicationEventResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.communicationEvent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommunicationEventUpdateComponent,
    resolve: {
      communicationEvent: CommunicationEventResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.communicationEvent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
