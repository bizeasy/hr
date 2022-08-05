import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommunicationEventType, CommunicationEventType } from 'app/shared/model/communication-event-type.model';
import { CommunicationEventTypeService } from './communication-event-type.service';
import { CommunicationEventTypeComponent } from './communication-event-type.component';
import { CommunicationEventTypeDetailComponent } from './communication-event-type-detail.component';
import { CommunicationEventTypeUpdateComponent } from './communication-event-type-update.component';

@Injectable({ providedIn: 'root' })
export class CommunicationEventTypeResolve implements Resolve<ICommunicationEventType> {
  constructor(private service: CommunicationEventTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommunicationEventType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((communicationEventType: HttpResponse<CommunicationEventType>) => {
          if (communicationEventType.body) {
            return of(communicationEventType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommunicationEventType());
  }
}

export const communicationEventTypeRoute: Routes = [
  {
    path: '',
    component: CommunicationEventTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.communicationEventType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommunicationEventTypeDetailComponent,
    resolve: {
      communicationEventType: CommunicationEventTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.communicationEventType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommunicationEventTypeUpdateComponent,
    resolve: {
      communicationEventType: CommunicationEventTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.communicationEventType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommunicationEventTypeUpdateComponent,
    resolve: {
      communicationEventType: CommunicationEventTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.communicationEventType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
