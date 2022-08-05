import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFacilityEquipment, FacilityEquipment } from 'app/shared/model/facility-equipment.model';
import { FacilityEquipmentService } from './facility-equipment.service';
import { FacilityEquipmentComponent } from './facility-equipment.component';
import { FacilityEquipmentDetailComponent } from './facility-equipment-detail.component';
import { FacilityEquipmentUpdateComponent } from './facility-equipment-update.component';

@Injectable({ providedIn: 'root' })
export class FacilityEquipmentResolve implements Resolve<IFacilityEquipment> {
  constructor(private service: FacilityEquipmentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFacilityEquipment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((facilityEquipment: HttpResponse<FacilityEquipment>) => {
          if (facilityEquipment.body) {
            return of(facilityEquipment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FacilityEquipment());
  }
}

export const facilityEquipmentRoute: Routes = [
  {
    path: '',
    component: FacilityEquipmentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityEquipment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FacilityEquipmentDetailComponent,
    resolve: {
      facilityEquipment: FacilityEquipmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityEquipment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FacilityEquipmentUpdateComponent,
    resolve: {
      facilityEquipment: FacilityEquipmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityEquipment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FacilityEquipmentUpdateComponent,
    resolve: {
      facilityEquipment: FacilityEquipmentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'hrApp.facilityEquipment.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
