import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContactMechType, ContactMechType } from 'app/shared/model/contact-mech-type.model';
import { ContactMechTypeService } from './contact-mech-type.service';
import { ContactMechTypeComponent } from './contact-mech-type.component';
import { ContactMechTypeDetailComponent } from './contact-mech-type-detail.component';
import { ContactMechTypeUpdateComponent } from './contact-mech-type-update.component';

@Injectable({ providedIn: 'root' })
export class ContactMechTypeResolve implements Resolve<IContactMechType> {
  constructor(private service: ContactMechTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContactMechType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contactMechType: HttpResponse<ContactMechType>) => {
          if (contactMechType.body) {
            return of(contactMechType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContactMechType());
  }
}

export const contactMechTypeRoute: Routes = [
  {
    path: '',
    component: ContactMechTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.contactMechType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContactMechTypeDetailComponent,
    resolve: {
      contactMechType: ContactMechTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.contactMechType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContactMechTypeUpdateComponent,
    resolve: {
      contactMechType: ContactMechTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.contactMechType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContactMechTypeUpdateComponent,
    resolve: {
      contactMechType: ContactMechTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.contactMechType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
