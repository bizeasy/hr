import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContactMech, ContactMech } from 'app/shared/model/contact-mech.model';
import { ContactMechService } from './contact-mech.service';
import { ContactMechComponent } from './contact-mech.component';
import { ContactMechDetailComponent } from './contact-mech-detail.component';
import { ContactMechUpdateComponent } from './contact-mech-update.component';

@Injectable({ providedIn: 'root' })
export class ContactMechResolve implements Resolve<IContactMech> {
  constructor(private service: ContactMechService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContactMech> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contactMech: HttpResponse<ContactMech>) => {
          if (contactMech.body) {
            return of(contactMech.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContactMech());
  }
}

export const contactMechRoute: Routes = [
  {
    path: '',
    component: ContactMechComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'hrApp.contactMech.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContactMechDetailComponent,
    resolve: {
      contactMech: ContactMechResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.contactMech.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContactMechUpdateComponent,
    resolve: {
      contactMech: ContactMechResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.contactMech.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContactMechUpdateComponent,
    resolve: {
      contactMech: ContactMechResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.contactMech.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
